package org.owpk.cli;

import org.owpk.config.ApplicationConstants;
import org.owpk.config.LlmSupports;
import org.owpk.config.VersionManager;
import org.owpk.config.properties.PropertiesManager;
import org.owpk.service.LlmService;
import org.owpk.service.LlmServiceFactory;
import org.owpk.service.dialog.DialogRepository;
import org.slf4j.Logger;

import io.micronaut.logging.LogLevel;
import io.micronaut.logging.LoggingSystem;
import jakarta.inject.Inject;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = ApplicationConstants.APP_NAME, description = "", mixinStandardHelpOptions = true)
public class LlmCommand implements Runnable {

    private final LlmServiceFactory llmServiceFactory;
    private final PropertiesManager propertiesManager;
    private final DialogRepository dialogRepository;
    private final LoggingSystem loggingSystem;
    private final VersionManager versionManager;

    @Inject
    public LlmCommand(LlmServiceFactory llmServiceFactory, PropertiesManager propertiesManager,
            DialogRepository dialogRepository, LoggingSystem loggingSystem, VersionManager versionManager) {
        this.llmServiceFactory = llmServiceFactory;
        this.propertiesManager = propertiesManager;
        this.dialogRepository = dialogRepository;
        this.loggingSystem = loggingSystem;
        this.versionManager = versionManager;
    }

    @Option(names = { "-v", "--version" }, description = "Version info")
    public void version(boolean version) {
        if (version) {
            System.out.println(
                    versionManager.getVersionInfo().version());
            System.exit(0);
        }
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
        chat(message);
    }

    private LlmService getLlmService() {
        var llm = propertiesManager.getApplicationProperties().getDefaulProvider();
        return llmServiceFactory.createLlmService(llm);
    }

}
