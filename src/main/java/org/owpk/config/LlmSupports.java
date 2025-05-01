package org.owpk.config;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * This class contains the supported LLM providers.
 * It is used to check if a provider is supported or not.
 */
public final class LlmSupports {

	public static final List<KnownLlm> SUPPORTED_PROVIDERS = List.of(
			KnownLlm.OLLAMA);

	@Getter
	public enum KnownLlm {
		OLLAMA("ollama", "http://localhost:11434"),
		OPENAI("openai", "https://api.openai.com"),
		MISTRAL("mistral", "https://api.mistral.ai");

		private final String name;
		private final String defaultUrl;

		KnownLlm(String name, String defaultUrl) {
			this.name = name;
			this.defaultUrl = defaultUrl;
		}

		public static KnownLlm of(String name) {
			return Arrays.stream(KnownLlm.values())
					.collect(Collectors.toMap(it -> it.getName(), Function.identity()))
					.getOrDefault(name, OLLAMA);
		}
	}
}
