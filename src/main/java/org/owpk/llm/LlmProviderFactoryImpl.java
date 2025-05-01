package org.owpk.llm;

import org.owpk.config.SupportedLlm;
import org.owpk.config.properties.model.LlmProviderProperties;
import org.owpk.llm.client.ollama.client.OllamaClientLowLevelImpl;
import org.owpk.llm.provider.LlmProvider;
import org.owpk.llm.provider.LlmProviderFactory;
import org.owpk.llm.provider.OllamaProvider;
import org.owpk.llm.provider.dialog.DialogRepository;
import org.owpk.llm.provider.role.RolesManager;

import io.micronaut.http.client.StreamingHttpClient;
import io.micronaut.http.client.annotation.Client;

public class LlmProviderFactoryImpl implements LlmProviderFactory {
	private final StreamingHttpClient streamingHttpClient;
	private final DialogRepository dialogRepository;
	private final RolesManager rolesManager;
	// private final LlmProvider<?> defaultProvider;

	public LlmProviderFactoryImpl(@Client StreamingHttpClient streamingHttpClient,
			DialogRepository dialogRepository, RolesManager rolesManager) {
		this.streamingHttpClient = streamingHttpClient;
		this.dialogRepository = dialogRepository;
		this.rolesManager = rolesManager;
	}

	@Override
	public LlmProvider<?> createProvider(SupportedLlm llmType, LlmProviderProperties properties) {
		if (llmType == SupportedLlm.OLLAMA) {
			var ollamaClient = new OllamaClientLowLevelImpl(streamingHttpClient, properties.getUrl());
			return new OllamaProvider(ollamaClient, properties, dialogRepository, rolesManager);
		}
		throw new IllegalArgumentException("Unsupported LLM provider: " + llmType);
	}

}
