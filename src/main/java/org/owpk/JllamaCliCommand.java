package org.owpk;

import org.owpk.llm.ollama.client.OllamaClient;
import org.owpk.llm.ollama.client.model.OllamaGenerateRequest;

import io.micronaut.configuration.picocli.PicocliRunner;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "ollama-cli", description = "...", mixinStandardHelpOptions = true)
public class JllamaCliCommand implements Runnable {

    @Option(names = { "-v", "--verbose" }, description = "...")
    boolean verbose;

    private final OllamaClient client;

    @Inject
    public JllamaCliCommand(OllamaClient ollamaClient) {
        this.client = ollamaClient;
    }

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(JllamaCliCommand.class, args);
    }

    public void run() {
        var request = OllamaGenerateRequest.builder()
                .model("qwen2.5-coder:7b")
                .prompt("write c++ simple programm")
                .stream(true)
                .build();

        client.generate(request)
                .doOnNext(response -> {
                    if (response.getResponse() != null) {
                        System.out.print(response.getResponse());
                    }
                })
                .doOnComplete(() -> System.out.println("\nГотово"))
                .blockLast();
    }
}
