#!/usr/bin/env bash

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Project root
PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BUILD_DIR="${PROJECT_ROOT}/build/native/nativeCompile"
BINARY_NAME="jllama"
INSTALLED_BINARY="${BUILD_DIR}/${BINARY_NAME}"

# Installation directory (user's local bin)
INSTALL_DIR="${HOME}/.local/bin"
INSTALLED_BINARY_PATH="${INSTALL_DIR}/${BINARY_NAME}"

echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}       Jllama CLI Install Script       ${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""

# Function to print error and exit
error_exit() {
    echo -e "${RED}ERROR: $1${NC}" >&2
    exit 1
}

# Function to print success
print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

# Function to print warning
print_warning() {
    echo -e "${YELLOW}WARNING: $1${NC}"
}

# Check if Java 21+ (GraalVM) is installed
check_java() {
    echo "Checking Java 21+ (GraalVM) installation..."
    
    if ! command -v java &> /dev/null; then
        error_exit "Java is not installed. Please install GraalVM Java 21+"
    fi
    
    # Get Java version
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
    
    if [[ "$JAVA_VERSION" -lt 21 ]]; then
        error_exit "Java 21 is required for GraalVM native image. Current version: $JAVA_VERSION"
    fi
    
    if [[ "$JAVA_VERSION" -gt 21 ]]; then
        print_warning "Java version $JAVA_VERSION detected. GraalVM native image build is tested with Java 21."
    fi
    
    # Check if it's GraalVM
    java -version 2>&1 | grep -qi "graalvm"
    if [[ $? -eq 0 ]]; then
        print_success "GraalVM Java $JAVA_VERSION found"
    else
        print_warning "Java $JAVA_VERSION found, but it may not be GraalVM"
        print_warning "For native image build, GraalVM Java 21 is required"
        print_warning "Current java -version output:"
        java -version 2>&1 | sed 's/^/    /'
    fi
}

# Check if GRAALVM_HOME is set (for native image)
check_graalvm() {
    echo "Checking GraalVM environment..."
    
    local is_graalvm=false
    
    # Check if java -version shows GraalVM
    java -version 2>&1 | grep -qi "graalvm"
    if [[ $? -eq 0 ]]; then
        is_graalvm=true
    fi
    
    if [[ -n "${GRAALVM_HOME}" ]]; then
        if [[ "$is_graalvm" == "true" ]]; then
            print_success "GRAALVM_HOME is set: ${GRAALVM_HOME}"
        else
            print_warning "GRAALVM_HOME is set but java -version doesn't show GraalVM"
        fi
    elif [[ -n "${GRAALVM_ROOT}" ]]; then
        GRAALVM_HOME="${GRAALVM_ROOT}"
        if [[ "$is_graalvm" == "true" ]]; then
            print_success "GRAALVM_ROOT found: ${GRAALVM_HOME}"
        else
            print_warning "GRAALVM_ROOT is set but java -version doesn't show GraalVM"
        fi
    else
        # Try to find GraalVM via JAVA_HOME
        JAVA_HOME_VAL="${JAVA_HOME:-}"
        if [[ -n "$JAVA_HOME_VAL" ]]; then
            if [[ "$is_graalvm" == "true" ]]; then
                GRAALVM_HOME="${JAVA_HOME_VAL}"
                print_success "GraalVM found via JAVA_HOME: ${GRAALVM_HOME}"
            else
                print_warning "JAVA_HOME is set but may not be GraalVM"
            fi
        else
            if [[ "$is_graalvm" == "true" ]]; then
                print_success "GraalVM detected (GRAALVM_HOME not set, using current Java)"
            else
                print_warning "GRAALVM_HOME and JAVA_HOME are not set"
            fi
        fi
    fi
    
    # Critical check: native-image must be available for native build
    if ! command -v native-image &> /dev/null; then
        if [[ "$is_graalvm" != "true" ]]; then
            error_exit "GraalVM native-image tool not found. Please install GraalVM Java 21+ and ensure native-image is available"
        fi
    fi
}

# Check if native-image is available (already checked in check_graalvm)
check_native_image() {
    echo "Checking native-image tool..."
    
    if command -v native-image &> /dev/null; then
        NATIVE_IMAGE_VERSION=$(native-image --version 2>&1 | head -n 1)
        print_success "native-image found: ${NATIVE_IMAGE_VERSION}"
    else
        # This should have been caught in check_graalvm
        error_exit "native-image tool not found. Please install GraalVM Java 21+"
    fi
}

# Check if gradle wrapper exists
check_gradle() {
    echo "Checking Gradle wrapper..."
    
    if [[ ! -f "${PROJECT_ROOT}/gradlew" ]]; then
        error_exit "Gradle wrapper not found. Make sure you're in the project root directory"
    fi
    
    chmod +x "${PROJECT_ROOT}/gradlew"
    print_success "Gradle wrapper found"
}

# Check if jllama binary is already installed
check_existing_installation() {
    echo "Checking for existing jllama installation..."
    
    if command -v "${BINARY_NAME}" &> /dev/null; then
        EXISTING_PATH=$(which "${BINARY_NAME}")
        print_warning "jllama already installed at: ${EXISTING_PATH}"
        echo ""
        read -p "Do you want to remove it and reinstall? (y/n): " -n 1 -r
        echo ""
        
        if [[ $REPLY =~ ^[Yy]$ ]]; then
            rm -f "${EXISTING_PATH}"
            print_success "Removed existing binary"
        else
            echo "Installation cancelled by user"
            exit 0
        fi
    else
        print_success "No existing jllama installation found"
    fi
}

# Check if ~/.local/bin exists and is in PATH
check_install_dir() {
    echo "Checking installation directory..."
    
    if [[ ! -d "${INSTALL_DIR}" ]]; then
        mkdir -p "${INSTALL_DIR}"
        print_success "Created ${INSTALL_DIR}"
    fi
    
    # Check if INSTALL_DIR is in PATH
    if [[ ":${PATH}:" != *":${INSTALL_DIR}:"* ]]; then
        print_warning "${INSTALL_DIR} is not in PATH"
        echo ""
        echo "Add this to your shell profile (~/.bashrc, ~/.zshrc, etc.):"
        echo "  export PATH=\"${INSTALL_DIR}:\$PATH\""
        echo ""
        read -p "Continue anyway? (y/n): " -n 1 -r
        echo ""
        
        if [[ ! $REPLY =~ ^[Yy]$ ]]; then
            exit 0
        fi
    fi
}

# Build native image
build_native_image() {
    echo ""
    echo "Building native image with GraalVM..."
    echo ""
    
    cd "${PROJECT_ROOT}"
    
    if ./gradlew clean nativeBuild; then
        print_success "Native image build completed"
    else
        error_exit "Native image build failed. Check the error messages above"
    fi
}

# Verify binary was created
verify_binary() {
    echo "Verifying binary..."
    
    if [[ ! -f "${INSTALLED_BINARY}" ]]; then
        error_exit "Binary not found at ${INSTALLED_BINARY}. Build may have failed"
    fi
    
    chmod +x "${INSTALLED_BINARY}"
    print_success "Binary verified and made executable"
}

# Install binary to PATH
install_binary() {
    echo ""
    echo "Installing binary to ${INSTALL_DIR}..."
    
    cp "${INSTALLED_BINARY}" "${INSTALL_DIR}/${BINARY_NAME}"
    chmod +x "${INSTALL_DIR}/${BINARY_NAME}"
    
    print_success "Jllama CLI installed to ${INSTALL_DIR}/${BINARY_NAME}"
}

# Print installation summary
print_summary() {
    echo ""
    echo -e "${GREEN}========================================${NC}"
    echo -e "${GREEN}         Installation Complete!        ${NC}"
    echo -e "${GREEN}========================================${NC}"
    echo ""
    echo "Jllama CLI has been installed successfully!"
    echo ""
    echo "Usage:"
    echo "  jllama \"Your prompt here\""
    echo "  jllama chat \"Hello\""
    echo "  jllama -r shell generate \"list files\""
    echo ""
    echo "Configuration file will be created at:"
    echo "  ~/.jllama/jllama.yaml"
    echo ""
    echo "Don't forget to set your model name in the config!"
    echo ""
}

# Main execution
main() {
    check_java
    check_graalvm
    check_native_image
    check_gradle
    check_existing_installation
    check_install_dir
    
    echo ""
    echo "All prerequisites checked. Starting build..."
    echo ""
    
    build_native_image
    verify_binary
    install_binary
    
    print_summary
}

main "$@"
