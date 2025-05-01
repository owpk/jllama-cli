package org.owpk;

import org.owpk.config.LlmSupports;
import org.owpk.service.LlmServiceFactory;
import org.owpk.service.role.def.DefaultRoles;

import io.micronaut.configuration.picocli.PicocliRunner;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "ollama-cli", description = "...", mixinStandardHelpOptions = true)
public class JllamaCliCommand implements Runnable {

    @Option(names = { "-v", "--verbose" }, description = "...")
    boolean verbose;

    @Option(names = { "-p", "--provider" }, description = "LLM provider identifier (e.g. ollama)")
    String provider;

    private final LlmServiceFactory llmServiceFactory;

    @Inject
    public JllamaCliCommand(LlmServiceFactory llmFactory) {
        this.llmServiceFactory = llmFactory;

    }

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(JllamaCliCommand.class, args);
    }

    public void run() {
        var ollama = LlmSupports.KnownLlm.OLLAMA;
        var llmService = llmServiceFactory.createLlmService(ollama);

        llmService.generate("Самые крупные животные на планете", DefaultRoles.getDEFAULT().getId())
                .doOnNext(it -> System.out.print(it))
                .blockLast();
    }
}
