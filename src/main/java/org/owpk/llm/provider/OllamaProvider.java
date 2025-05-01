package org.owpk.llm.provider;

import java.util.List;
import java.util.Map;

import org.owpk.config.properties.model.LlmProviderProperties;
import org.owpk.llm.client.ollama.client.OllamaClient;
import org.owpk.llm.client.ollama.client.model.OllamaGenerateRequest;
import org.owpk.llm.provider.dialog.DialogRepository;
import org.owpk.llm.provider.dialog.Message;
import org.owpk.llm.provider.mcp.McpMessage;
import org.owpk.llm.provider.model.ChatRequest;
import org.owpk.llm.provider.role.Role;
import org.owpk.llm.provider.role.RolesManager;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OllamaProvider extends LlmProvider<OllamaClient> {

	public OllamaProvider(OllamaClient ollamaClient, LlmProviderProperties properties,
			DialogRepository dialogRepository, RolesManager roleManager) {
		super(ollamaClient, properties, dialogRepository, roleManager);
	}

	@Override
	protected void onClientUpdate(OllamaClient newClient, LlmProviderProperties newProperties) {
		// Implement client switching logic
	}

	@Override
	public Flux<String> generate(String prompt, String roleId) {
		var role = getRoleManager().getByRoleId(roleId);
		return getApiClient()
				.generate(OllamaGenerateRequest.builder()
						.model(getProperties().getModel())
						.prompt(prompt)
						.system(role.getPrompt())
						.stream(true)
						.build())
				.map(response -> response.getResponse());
	}

	@Override
	public Flux<String> chat(List<ChatRequest> messages) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'embed'");
	}

	@Override
	public Mono<double[]> embed(String text) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'embed'");
	}

	@Override
	public Mono<String> startDialog(String roleId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'startDialog'");
	}

	@Override
	public Mono<Void> saveDialog(String dialogId, List<Message> messages) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'saveDialog'");
	}

	@Override
	public Flux<Message> loadDialog(String dialogId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'loadDialog'");
	}

	@Override
	public Flux<Role> getAvailableRoles() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getAvailableRoles'");
	}

	@Override
	public Mono<Role> setRole(String dialogId, String roleId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setRole'");
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
