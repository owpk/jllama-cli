package org.owpk.cli;

import java.util.concurrent.Callable;

import org.owpk.config.LlmSupports;
import org.owpk.config.properties.PropertiesManager;
import org.owpk.service.LlmService;
import org.owpk.service.LlmServiceFactory;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "ollama-cli", description = "...", mixinStandardHelpOptions = true)
public class LlmCommand implements Callable<Integer> {

    private final LlmServiceFactory llmServiceFactory;
    private final PropertiesManager propertiesManager;

    @Inject
    public LlmCommand(LlmServiceFactory llmFactory, PropertiesManager propertiesManager) {
        this.llmServiceFactory = llmFactory;
        this.propertiesManager = propertiesManager;
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

    @Command(name = "generate", aliases = { "gen", "g" }, description = "Generate text using LLM")
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

    @Command(name = "chat", aliases = { "c" }, description = "Chat with LLM")
    public void chat(@Parameters(description = "Chat message") String[] message) {
        if (message.length == 0) {
            System.out.println("Please provide a message to chat.");
            return;
        }
        var llmService = getLlmService();
        var defaultRole = propertiesManager.getApplicationProperties().getDefaultRoleName();

        llmService.chat(
                String.join(" ", message),
                defaultRole, 10)
                .doOnNext(it -> System.out.print(it))
                .blockLast();
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Please provide a command.");
        return 1;
    }

    private LlmService getLlmService() {
        var llm = propertiesManager.getApplicationProperties().getDefaulProvider();
        return llmServiceFactory.createLlmService(llm);
    }

}
