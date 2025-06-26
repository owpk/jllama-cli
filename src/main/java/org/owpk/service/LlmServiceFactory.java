package org.owpk.service;

import io.micronaut.json.JsonMapper;
import org.owpk.config.LlmSupports;
import org.owpk.config.properties.PropertiesManager;
import org.owpk.config.properties.model.LlmProviderProperties;
import org.owpk.llm.client.ollama.client.OllamaClientLowLevelImpl;
import org.owpk.llm.client.openai.client.OpenAiClientImpl;
import org.owpk.llm.provider.LlmProvider;
import org.owpk.llm.provider.OllamaProvider;
import org.owpk.llm.provider.OpenAiProvider;
import org.owpk.llm.provider.auth.ApiKeyProvider;
import org.owpk.llm.provider.auth.DefaultApiKeyProvider;
import org.owpk.service.dialog.DialogRepository;
import org.owpk.service.role.RolesManager;

import io.micronaut.http.client.StreamingHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.sse.SseClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LlmServiceFactory {
	private final @Client StreamingHttpClient streamingHttpClient;
	private final JsonMapper jsonMapper;
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
			return new OllamaProvider(ollamaClient, properties);
		} else if (llm == LlmSupports.KnownLlm.OPENAI) {
			var openAiClient = new OpenAiClientImpl(streamingHttpClient, jsonMapper, properties.getUrl(),
					properties.getApiKey());
			return new OpenAiProvider(openAiClient, properties, apiKeyProvider);
		}
		throw new IllegalArgumentException("Unsupported LLM provider: " + llm);
	}

	private ApiKeyProvider getApiKeyProvider(LlmProviderProperties properties) {
		return new DefaultApiKeyProvider(properties.getApiKey());
	}
}
