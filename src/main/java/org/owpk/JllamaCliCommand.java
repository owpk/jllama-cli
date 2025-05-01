package org.owpk;

import org.owpk.config.SupportedLlm;
import org.owpk.config.properties.PropertiesManager;
import org.owpk.llm.provider.LlmProviderFactory;
import org.owpk.llm.provider.role.def.DefaultRoles;

import io.micronaut.configuration.picocli.PicocliRunner;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "ollama-cli", description = "...", mixinStandardHelpOptions = true)
public class JllamaCliCommand implements Runnable {

    @Option(names = { "-v", "--verbose" }, description = "...")
    boolean verbose;

    private final LlmProviderFactory llmProviderFactory;
    private final PropertiesManager propertiesManager;

    @Inject
    public JllamaCliCommand(LlmProviderFactory llmProviderFactory,
            PropertiesManager propertiesManager) {
        this.llmProviderFactory = llmProviderFactory;
        this.propertiesManager = propertiesManager;
    }

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(JllamaCliCommand.class, args);
    }

    public void run() {
        var ollamaProperties = propertiesManager.getLlmProviderProperties(SupportedLlm.OLLAMA);
        var llmProvider = llmProviderFactory.createProvider(SupportedLlm.OLLAMA, ollamaProperties);
        llmProvider.generate("search all java projects in home dir", DefaultRoles.getSHELL().getId())
                .doOnNext(it -> System.out.print(it))
                .doOnComplete(() -> System.out.println(""))
                .blockLast();
    }
}
