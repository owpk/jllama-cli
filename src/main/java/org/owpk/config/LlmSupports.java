package org.owpk.config;

import java.util.List;

/**
 * This class contains the supported LLM providers.
 * It is used to check if a provider is supported or not.
 */
public final class LlmSupports {

	public static final List<SupportedLlm> SUPPORTED_PROVIDERS = List.of(
			SupportedLlm.OLLAMA);
}
