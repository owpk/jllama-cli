package org.owpk.llm.provider.auth;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultApiKeyProvider implements ApiKeyProvider {
	private final String apiKey;

	@Override
	public String getApiKey() {
		return apiKey;
	}

	@Override
	public boolean isApiKeyValid() {
		return apiKey != null && !apiKey.isEmpty();
	}

}
