package org.owpk.llm.client.openai.client;

import java.net.URI;

import org.owpk.llm.client.ClientException;
import org.owpk.llm.client.openai.model.OpenAIChatRequest;
import org.owpk.llm.client.openai.model.OpenAIChatResponse;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.StreamingHttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.uri.UriBuilder;
import reactor.core.publisher.Flux;

public class OpenAiClientImpl implements OpenAiClient {
    private final StreamingHttpClient httpClient;
    private final String baseUrl;
    private final String apiKey;

    public OpenAiClientImpl(StreamingHttpClient httpClient, String baseUrl, String apiKey) {
        this.httpClient = httpClient;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    public Flux<OpenAIChatResponse> chat(OpenAIChatRequest request) {
        URI uri = UriBuilder.of(baseUrl).path("/v1/chat/completions").build();
        HttpRequest<OpenAIChatRequest> httpRequest = HttpRequest.POST(uri, request)
                .contentType(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .bearerAuth(apiKey);
        return Flux.from(httpClient.jsonStream(httpRequest, OpenAIChatResponse.class))
                .onErrorMap(HttpClientResponseException.class, e ->
                        new ClientException("OpenAI API error: " + e.getMessage(), e))
                .onErrorMap(e ->
                        !(e instanceof ClientException),
                        e -> new ClientException("Unexpected error: " + e.getMessage(), e));
    }

    // Можно добавить другие методы для поддержки дополнительных эндпоинтов OpenAI
}
