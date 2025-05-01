package org.owpk.config;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public enum SupportedLlm {
	OLLAMA("ollama", "http://localhost:11434"),
	OPENAI("openai", "https://api.openai.com"),
	MISTRAL("mistral", "https://api.mistral.ai");

	private final String name;
	private final String defaultUrl;

	SupportedLlm(String name, String defaultUrl) {
		this.name = name;
		this.defaultUrl = defaultUrl;
	}

	public static SupportedLlm of(String name) {
		return Arrays.stream(SupportedLlm.values())
				.collect(Collectors.toMap(it -> it.getName(), Function.identity()))
				.getOrDefault(name, OLLAMA);
	}
}
