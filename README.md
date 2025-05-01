# Jllama CLI

A command-line interface for interacting with Ollama LLM models, designed for seamless integration with shell scripts and command-line workflows.

## Features

- Simple command-line interface for text generation and chat
- System roles for specialized tasks (shell commands, git commits, etc.)
- Chat history preservation
- Easy integration with shell pipelines
- Multiple LLM provider support (currently focused on Ollama)

## Installation
 ### 1. Manual (preferred)
 #### Option 1: Super fast native image executable
 > YOU NEED JAVA 21+ GRAAL VM SDK
- build native image
```sh
./gradlew clean nativeBuild
```
 - then executable file should be in `${projectRoot}/build/native/nativeCompile/jllama`

- Run it with:
```sh
./jllama chat "hello help me please"
```
 #### Option 2: Very stable but slow plain `jar` file:
 > YOU NEED JAVA 21+
- build project
 ```sh
 ./gradlew clean build
 ```
 - then jar file should be in `${projectRoot}/build/libs/jllama-cli-*-all.jar`

- Run it with:
```sh
java -jar jllama-cli-*-all.jar chat "hello help me please"
```

### 2. Or download `native executable` or `jar` from releases
- (testing on different platforms in progress)

## Configuration

Configuration is stored in:
- `~/.jllama/jllama.yaml` - main configuration
- `~/.jllama/chats/` - chat history

## Usage

### Basic Commands

Generate text:
```bash
jllama generate --role "roleId from jllama.yaml config" "Your prompt here"
```

Chat mode:
```bash
jllama chat "Your message"
```

### Predefined System Roles

The CLI comes with predefined roles:

1. `default` - General-purpose helpful assistant
2. `shell` - Generates shell commands for your OS
3. `git-autcommit` - Creates commit messages based on changes
4. `describe-shell` - Explains shell commands

Set a role:
```bash
jllama -r shell generate "create a directory named test and cd into it"
```

### Pipeline Integration Examples

Generate commit message based on git changes:
```bash
jllama -r git-autcommit generate "$(git diff)"
```

Get shell command explanation:
```bash
jllama -r describe-shell generate "ls -la | grep '^d'"
```

### Configuration Options

Set LLM provider:
```bash
jllama -p ollama generate "Hello"
```

## Default Configuration

- Default provider: Ollama (http://localhost:11434)
- Default role: general-purpose assistant
- Chat history is automatically preserved

## Project Structure

The application is built using:
- Micronaut framework for dependency injection and HTTP client
- Picocli for command-line parsing
- Reactive programming with Project Reactor
- YAML for configuration

## Roadmap

### Planned LLM Provider Support
- OpenAI API integration
- Mistral AI support
- Others maybe :)

### Upcoming Features
- Image generating / processing
- "suffix" support
- Advanced parameter configuration (temperature, top_p, etc.)
- Model Context Protocol (MCP) support for standardized LLM interactions
- Context window management
- Ebeddings
- API key management

## License

MIT

