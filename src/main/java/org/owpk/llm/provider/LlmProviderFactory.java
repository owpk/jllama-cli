package org.owpk.llm.provider;

import org.owpk.config.SupportedLlm;
import org.owpk.config.properties.model.LlmProviderProperties;

public interface LlmProviderFactory {
	LlmProvider<?> createProvider(SupportedLlm llmType, LlmProviderProperties properties);
}
