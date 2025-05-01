package org.owpk.llm.provider;

import java.util.List;
import java.util.Map;

import org.owpk.config.properties.model.LlmProviderProperties;
import org.owpk.llm.client.ollama.client.OllamaClient;
import org.owpk.llm.client.ollama.client.model.OllamaChatMessage;
import org.owpk.llm.client.ollama.client.model.OllamaChatRequest;
import org.owpk.llm.client.ollama.client.model.OllamaGenerateRequest;
import org.owpk.llm.provider.mcp.McpMessage;
import org.owpk.llm.provider.model.ChatRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OllamaProvider extends LlmProvider<OllamaClient> {

	public OllamaProvider(OllamaClient ollamaClient, LlmProviderProperties properties) {
		super(ollamaClient, properties);
	}

	@Override
	protected void onClientUpdate(OllamaClient newClient, LlmProviderProperties newProperties) {
		// Implement client switching logic
	}

	@Override
	public Flux<String> generate(String prompt, String rolePrompt) {
		return getApiClient()
				.generate(OllamaGenerateRequest.builder()
						.model(getProperties().getModel())
						.prompt(prompt)
						.system(rolePrompt)
						.stream(true)
						.build())
				.map(response -> response.getResponse());
	}

	@Override
	public Flux<String> chat(List<ChatRequest> messages) {
		return getApiClient()
				.chat(OllamaChatRequest.builder()
						.model(getProperties().getModel())
						.messages(messages.stream().map(it -> new OllamaChatMessage()).toList())
						.stream(true)
						.build())
				.map(response -> response.getMessage().getContent());
	}

	@Override
	public Mono<double[]> embed(String text) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'embed'");
	}

	@Override
	protected Mono<Void> handleMcpMessage(McpMessage message) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'handleMcpMessage'");
	}

	@Override
	protected Map<String, Object> getMcpMetadata() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getMcpMetadata'");
	}

}
