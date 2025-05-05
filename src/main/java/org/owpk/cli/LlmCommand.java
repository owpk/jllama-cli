package org.owpk.cli;

import java.util.Arrays;
import java.util.Scanner;

import org.owpk.config.ApplicationConstants;
import org.owpk.config.LlmSupports;
import org.owpk.config.VersionManager;
import org.owpk.config.properties.PropertiesManager;
import org.owpk.service.LlmService;
import org.owpk.service.LlmServiceFactory;
import org.owpk.service.dialog.DialogRepository;
import org.owpk.service.role.def.DefaultRoles;
import org.slf4j.Logger;

import io.micronaut.logging.LogLevel;
import io.micronaut.logging.LoggingSystem;
import jakarta.inject.Inject;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = ApplicationConstants.APP_NAME, description = "", mixinStandardHelpOptions = true, versionProvider = VersionManager.class)
public class LlmCommand implements Runnable {

    private final LlmServiceFactory llmServiceFactory;
    private final PropertiesManager propertiesManager;
    private final DialogRepository dialogRepository;
    private final LoggingSystem loggingSystem;

    @Inject
    public LlmCommand(LlmServiceFactory llmServiceFactory, PropertiesManager propertiesManager,
            DialogRepository dialogRepository, LoggingSystem loggingSystem) {
        this.llmServiceFactory = llmServiceFactory;
        this.propertiesManager = propertiesManager;
        this.dialogRepository = dialogRepository;
        this.loggingSystem = loggingSystem;
    }

    @Option(names = { "-p", "--provider" }, description = "LLM provider identifier (e.g. ollama)")
    public void provider(String provider) {
        LlmSupports.KnownLlm.of(provider);
        propertiesManager.getApplicationProperties().setDefaulProvider(provider);
    }

    @Option(names = { "-r", "--role-name" }, description = "LLM system role name")
    public void roleName(String roleId) {
        propertiesManager.getApplicationProperties().setDefaultRoleName(roleId);
    }

    @Option(names = { "-n", "--new-chat" }, description = "Create new chat")
    public void newChat(boolean newChat) {
        if (newChat) {
            dialogRepository.createNewDialog().block();
        }
    }

    @Option(names = "--log-level", description = "Set log level: ERROR | INFO | DEBUG", defaultValue = "ERROR", scope = CommandLine.ScopeType.INHERIT)
    public void setLogLevel(String logLevel) {
        loggingSystem.setLogLevel(Logger.ROOT_LOGGER_NAME, LogLevel.valueOf(logLevel));
    }

    @Parameters(description = "message")
    public String[] message;

    @Command(name = "generate", description = "Generate text using LLM")
    public void generate(@Parameters(description = "Text prompt") String[] prompt) {
        if (prompt.length == 0) {
            System.out.println("Please provide a message to chat.");
            return;
        }
        var llmService = getLlmService();
        var defaultRole = propertiesManager.getApplicationProperties().getDefaultRoleName();

        llmService.generate(String.join(" ", prompt), defaultRole)
                .doOnNext(it -> System.out.print(it))
                .blockLast();
    }

    @Command(name = "shell-mode", description = "Shell mode")
    public void shell(@Parameters(description = "Text prompt") String[] prompt) {
        if (prompt.length == 0) {
            System.out.println("Please provide a message to chat.");
            return;
        }
        var llmService = getLlmService();
        var roles = propertiesManager.getApplicationProperties().getRoles();
        var shellRole = Arrays.stream(roles)
                .filter(role -> role.getName().equalsIgnoreCase(DefaultRoles.getSHELL().getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("'shell' role not found"));

        var command = llmService.generate(String.join(" ", prompt), shellRole.getName())
                .doOnNext(it -> System.out.print(it))
                .collectList()
                .block();

        if (command == null || command.isEmpty()) {
            System.out.println("No command generated.");
            return;
        }

        System.out.print("Do you want to execute this command? (Y/n): ");

        try (var scanner = new Scanner(System.in)) {
            String userInput = scanner.nextLine().trim().toLowerCase();

            if (userInput != null && (!userInput.startsWith("n") || userInput.isBlank())) {
                try {
                    var processBuilder = new ProcessBuilder();
                    var cmd = processBuilder.command(command);
                    cmd.redirectOutput(ProcessBuilder.Redirect.PIPE);
                    var process = cmd.start();
                    process.waitFor();
                    System.out.println("Command executed successfully.");
                } catch (Exception e) {
                    System.err.println("Failed to execute command: " + e.getMessage());
                }
            } else {
                System.out.println("Command execution canceled.");
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    @Command(name = "chat", description = "Chat with LLM")
    public void chat(@Parameters(description = "Chat message") String[] message) {
        if (message.length == 0) {
            System.out.println("Please provide a message to chat.");
            return;
        }
        var llmService = getLlmService();
        var defaultRole = propertiesManager.getApplicationProperties().getDefaultRoleName();

        llmService.chat(
                String.join(" ", message),
                defaultRole, 4)
                .doOnNext(it -> System.out.print(it))
                .blockLast();
    }

    @Override
    public void run() {
        if (message == null || message.length == 0) {
            System.out.println("Please provide a message to chat.");
            // show help
            CommandLine.usage(this, System.out);
            return;
        }
        chat(message);
    }

    private LlmService getLlmService() {
        var llm = propertiesManager.getApplicationProperties().getDefaulProvider();
        return llmServiceFactory.createLlmService(llm);
    }

}
