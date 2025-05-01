package org.owpk.llm.provider;

import java.util.List;
import java.util.Map;

import org.owpk.config.properties.model.LlmProviderProperties;
import org.owpk.llm.provider.dialog.DialogRepository;
import org.owpk.llm.provider.dialog.Message;
import org.owpk.llm.provider.mcp.McpMessage;
import org.owpk.llm.provider.model.ChatRequest;
import org.owpk.llm.provider.role.Role;
import org.owpk.llm.provider.role.RolesManager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Getter
public abstract class LlmProvider<T> {
	private final T apiClient;
	protected final LlmProviderProperties properties;
	protected final DialogRepository dialogRepository;
	protected final RolesManager roleManager;

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
	public abstract Flux<String> generate(String prompt, String roleId);

	public abstract Flux<String> chat(List<ChatRequest> messages);

	public abstract Mono<double[]> embed(String text);

	/**
	 * Управление диалогами
	 */
	public abstract Mono<String> startDialog(String roleId);

	public abstract Mono<Void> saveDialog(String dialogId, List<Message> messages);

	public abstract Flux<Message> loadDialog(String dialogId);

	/**
	 * Управление ролями
	 */
	public abstract Flux<Role> getAvailableRoles();

	public abstract Mono<Role> setRole(String dialogId, String roleId);

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