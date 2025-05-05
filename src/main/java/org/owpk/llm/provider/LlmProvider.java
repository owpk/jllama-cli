package org.owpk.llm.provider;

import java.util.List;
import java.util.Map;

import org.owpk.config.properties.model.LlmProviderProperties;
import org.owpk.llm.provider.auth.ApiKeyProvider;
import org.owpk.llm.provider.mcp.McpMessage;
import org.owpk.llm.provider.model.ChatRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Getter
public abstract class LlmProvider<T> {
	private final T apiClient;
	protected final LlmProviderProperties properties;
	protected final ApiKeyProvider apiKeyProvider;

	public void updateClient(T newClient, LlmProviderProperties newProperties) {
		// Implement client switching logic
		// This could involve cleanup of old client, initialization of new one
		onClientUpdate(newClient, newProperties);
	}

	/**
	 * Hook method for provider-specific client update logic
	 */
	protected abstract void onClientUpdate(T newClient, LlmProviderProperties newProperties);

	/**
	 * Базовые операции с LLM
	 */
	public abstract Flux<String> generate(String prompt, String rolePrompt);

	public abstract Flux<String> chat(List<ChatRequest> messages);

	public abstract Mono<double[]> embed(String text);

	/**
	 * Подготовка к MCP (Model Control Protocol)
	 * 
	 * !!! work in progress !!!
	 */
	protected Mono<Void> handleMcpMessage(McpMessage message) {
		throw new UnsupportedOperationException("MCP is not supported for this provider");
	}

	protected Map<String, Object> getMcpMetadata() {
		throw new UnsupportedOperationException("MCP is not supported for this provider");
	}

}