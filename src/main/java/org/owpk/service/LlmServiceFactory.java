package org.owpk.service;

import org.owpk.config.LlmSupports;
import org.owpk.config.properties.PropertiesManager;
import org.owpk.config.properties.model.LlmProviderProperties;
import org.owpk.llm.client.ollama.client.OllamaClientLowLevelImpl;
import org.owpk.llm.provider.LlmProvider;
import org.owpk.llm.provider.OllamaProvider;
import org.owpk.llm.provider.auth.ApiKeyProvider;
import org.owpk.llm.provider.auth.DefaultApiKeyProvider;
import org.owpk.service.dialog.DialogRepository;
import org.owpk.service.role.RolesManager;

import io.micronaut.http.client.StreamingHttpClient;
import io.micronaut.http.client.annotation.Client;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LlmServiceFactory {
	private final @Client StreamingHttpClient streamingHttpClient;
	private final PropertiesManager propertiesManager;
	private final DialogRepository dialogRepository;
	private final RolesManager rolesManager;

	public LlmService createLlmService(LlmSupports.KnownLlm llm) {
		var properties = propertiesManager.getLlmProviderProperties(llm);
		return new LlmService(createProvider(llm, properties),
				dialogRepository, rolesManager);
	}

	public LlmService createLlmService(String provider) {
		var llm = LlmSupports.KnownLlm.of(provider);
		var properties = propertiesManager.getLlmProviderProperties(llm);
		return new LlmService(createProvider(llm, properties),
				dialogRepository, rolesManager);
	}

	private LlmProvider<?> createProvider(LlmSupports.KnownLlm llm, LlmProviderProperties properties) {
		var apiKeyProvider = getApiKeyProvider(properties);
		if (llm == LlmSupports.KnownLlm.OLLAMA) {
			var ollamaClient = new OllamaClientLowLevelImpl(streamingHttpClient, properties.getUrl());
			return new OllamaProvider(ollamaClient, properties, apiKeyProvider);
		}
		throw new IllegalArgumentException("Unsupported LLM provider: " + llm);
	}

	private ApiKeyProvider getApiKeyProvider(LlmProviderProperties properties) {
		return new DefaultApiKeyProvider(properties.getApiKey());
	}
}
