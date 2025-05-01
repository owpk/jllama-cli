package org.owpk.llm.client.ollama.client;

import org.owpk.llm.client.ollama.client.model.OllamaChatRequest;
import org.owpk.llm.client.ollama.client.model.OllamaChatResponse;
import org.owpk.llm.client.ollama.client.model.OllamaEmbeddingRequest;
import org.owpk.llm.client.ollama.client.model.OllamaEmbeddingResponse;
import org.owpk.llm.client.ollama.client.model.OllamaGenerateRequest;
import org.owpk.llm.client.ollama.client.model.OllamaGenerateResponse;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.StreamingHttpClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class OllamaClientLowLevelImpl implements OllamaClient {
	private final StreamingHttpClient httpClient;
	private final String baseUrl;

	public Flux<OllamaGenerateResponse> generate(OllamaGenerateRequest request) {
		return Flux.from(httpClient.jsonStream(
				HttpRequest.POST(baseUrl + "/api/generate", request)
						.accept(MediaType.APPLICATION_JSON_STREAM),
				OllamaGenerateResponse.class));
	}

	@Override
	public Flux<OllamaChatResponse> chat(OllamaChatRequest request) {
		return Flux.from(httpClient.jsonStream(
				HttpRequest.POST(baseUrl + "/api/chat", request)
						.accept(MediaType.APPLICATION_JSON_STREAM),
				OllamaChatResponse.class));
	}

	@Override
	public Mono<OllamaEmbeddingResponse> createEmbedding(OllamaEmbeddingRequest request) {
		return Mono.from(httpClient.retrieve(
				HttpRequest.POST(baseUrl + "/api/embeddings", request),
				OllamaEmbeddingResponse.class));
	}

}
