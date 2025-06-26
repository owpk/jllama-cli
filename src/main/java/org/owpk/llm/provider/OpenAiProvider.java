package org.owpk.llm.provider;

import java.util.List;
import java.util.Map;

import org.owpk.config.properties.model.LlmProviderProperties;
import org.owpk.llm.client.openai.client.OpenAiClient;
import org.owpk.llm.client.openai.model.OpenAIChatMessage;
import org.owpk.llm.client.openai.model.OpenAIChatRequest;
import org.owpk.llm.provider.auth.ApiKeyProvider;
import org.owpk.llm.provider.model.ChatRequest;
import org.owpk.llm.provider.model.MessageType;
import org.owpk.llm.provider.mcp.McpMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OpenAiProvider extends LlmProvider<OpenAiClient> {
    public OpenAiProvider(OpenAiClient client, LlmProviderProperties properties, ApiKeyProvider apiKeyProvider) {
        super(client, properties, apiKeyProvider);
    }

    @Override
    protected void onClientUpdate(OpenAiClient newClient, LlmProviderProperties newProperties) {
        // no-op
    }

    @Override
    public Flux<String> generate(String prompt, String rolePrompt) {
        ChatRequest chatRequest = ChatRequest.builder()
            .role(MessageType.USER)
            .message(prompt)
            .build();
        return chat(List.of(chatRequest));
    }

    @Override
    public Flux<String> chat(List<ChatRequest> messages) {
        var req = OpenAIChatRequest.builder()
                .model(getProperties().getModel())
                .messages(messages.stream().map(m -> OpenAIChatMessage.builder()
                        .role(m.getRole())
                        .content(m.getMessage())
                        .build()).toList())
                .stream(true)
                .build();
        return getApiClient().chat(req);
    }

    @Override
    public Mono<double[]> embed(String text) {
        // TODO: реализовать вызов embeddings endpoint
        return Mono.error(new UnsupportedOperationException("Not implemented"));
    }

    @Override
    protected Mono<Void> handleMcpMessage(McpMessage message) {
        return Mono.error(new UnsupportedOperationException("Not implemented"));
    }

    @Override
    protected Map<String, Object> getMcpMetadata() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
