# Jllama CLI

A command-line interface for Ollama LLM models, designed for seamless integration with shell scripts and command-line workflows.

## Features

- Command-line interface for text generation and chat
- System roles for specialized tasks (shell commands, git commits, etc.)
- Chat history persistence
- Shell pipeline integration
- Multiple LLM provider support (currently Ollama)

## Installation
### 1. Manual (preferred)
#### Option 1: Native image executable

> ⚠️ **Important**: GraalVM native image build may have some limitations:
>
> - Build process might fail on some platforms (especially Windows) due to missing development tools
> - GraalVM environment variables and paths must be properly configured
> - Reflection and dynamic class loading may require additional configuration
> - Build times vary significantly across machines
>
> Use the latest GraalVM version and ensure all dependencies are installed.

> ⚠️ REQUIRES JAVA 21+ GRAAL VM SDK
- Build native image:
```sh
./gradlew clean nativeBuild
```
- The executable will be created at `${projectRoot}/build/native/nativeCompile/jllama`

- Run with:
```sh
./jllama chat "Hello, help me please"
```

#### Option 2: Plain JAR file (slower but more stable):
> ⚠️ REQUIRES JAVA 21+
- Build project:
```sh
./gradlew clean build
```
- The JAR file will be at `${projectRoot}/build/libs/jllama-cli-*-all.jar`

- Run with:
```sh
java -jar jllama-cli-*-all.jar chat "Hello, help me please"
```

### 2. Download releases
- Native executable or JAR file (platform testing in progress)

## Setup and Configuration

### Initial Setup
The application creates a default configuration file on first launch. 
- Or you can find default `jllama.yaml` in the project root.

### Configuration Path
```
~/.jllama/jllama.yaml
```

### Default Configuration
```yaml
providers:
    ollama:
        baseUrl: "http://localhost:11434"  # Ollama server URL
        modelName: null                # ⚠️ Set your installed model name
```

### Required Configuration

1. **modelName**: Set your installed Ollama model
   Example for mistral:
   ```bash
   ollama pull mistral
   ```
   Then update config:
   ```yaml
   modelName: "mistral"
   ```

2. **baseUrl**: Change if your Ollama server uses a different address

### Verify Configuration

```bash
jllama "Hello, are you there?"
```

### Troubleshooting

If you see "model not found":
1. Check if the model is installed (`ollama list`)
2. Verify the model name in config
3. Ensure Ollama server is running (`ollama serve`)

## Usage

Basic usage:
```bash
jllama "write a simple program"  # chat is the default command
```

Use role:
```bash
jllama --role-name "role name" # or -r
```

Start new chat:
```bash
jllama --new-chat  # or -n
```

Generate text:
```bash
jllama generate "Your prompt here"
```

Chat mode:
```bash
jllama chat "Your message"
```

### Predefined System Roles

The CLI comes with predefined roles:

1. `default` - General-purpose helpful assistant
2. `shell` - Generates shell commands for your OS
3. `git-autocommit` - Creates commit messages based on changes
4. `describe-shell` - Explains shell commands


### Pipeline Integration Examples

Generate commit message based on git changes:
```bash
jllama -r git-autocommit generate "$(git diff)"
```

Get shell command explanation:
```bash
jllama -r describe-shell generate "ls -la | grep '^d'"
```

### Configuration Options

Set LLM provider:
Ollama is only supported and default by now
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
- Maybe others :)

### Upcoming Features
- Image generating/processing
- "Suffix" support
- Advanced parameter configuration (temperature, top_p, etc.)
- Model Context Protocol (MCP) support for standardized LLM interactions
- Context window management
- Embeddings API
- API key management

## License

MIT

