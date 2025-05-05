package org.owpk.llm.provider.auth;

/**
 * Basicaly for Ollama provider
 */
public class NoOpApiKeyProvider implements ApiKeyProvider {

	@Override
	public String getApiKey() {
		return null;
	}

	@Override
	public boolean isApiKeyValid() {
		return true;
	}
}
