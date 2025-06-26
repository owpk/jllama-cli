package org.owpk.llm.client.openai.client;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import io.micronaut.json.JsonMapper;
import org.owpk.llm.client.openai.model.OpenAIChatRequest;
import org.owpk.llm.client.openai.model.OpenAIChatResponse;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.StreamingHttpClient;
import io.micronaut.http.uri.UriBuilder;
import reactor.core.publisher.Flux;

/**
 * OpenAi comp models
 */
public class OpenRouterClientImpl implements OpenAiClient {
    private final StreamingHttpClient httpClient;
    private final JsonMapper mapper;
    private final String baseUrl;
    private final String apiKey;

    public OpenRouterClientImpl(StreamingHttpClient httpClient, JsonMapper mappr,
                                String baseUrl, String apiKey) {
        this.mapper = mappr;
        this.httpClient = httpClient;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    public Flux<String> chat(OpenAIChatRequest request) {
        URI uri = UriBuilder.of(baseUrl).path("/v1/chat/completions").build();
        HttpRequest<OpenAIChatRequest> httpRequest = HttpRequest.POST(uri, request)
                .contentType(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.of("text/event-stream"))
                .bearerAuth(apiKey);

        var byteStream = httpClient.dataStream(httpRequest);

        return Flux.from(byteStream)
                .map(buf -> buf.toString(StandardCharsets.UTF_8))
                .flatMap(chunk -> Flux.fromArray(chunk.split("\n")))
                .filter(line -> line.startsWith("data: "))
                .map(line -> line.substring("data: ".length()))
                .takeUntil(line -> line.equals("[DONE]"))
                .flatMap(this::mapData);
    }

    private Flux<String> mapData(String raw) {
        try {
            OpenAIChatResponse response = mapper.readValue(raw, OpenAIChatResponse.class);
            return opt(response.getChoices())
                    .filter(it -> !it.isEmpty())
                    .flatMap(it -> opt(it.getFirst()))
                    .flatMap(it -> opt(it.getDelta()))
                    .flatMap(it -> opt(it.getContent()))
                    .map(Flux::just).orElseThrow();

        } catch (Exception ignored) {}
        return Flux.empty(); // ничего не публикуем вместо null
    }

    private <T> Optional<T> opt(T obj) {
        return Optional.ofNullable(obj);
    }
}
