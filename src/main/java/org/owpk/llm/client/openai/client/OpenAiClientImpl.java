package org.owpk.llm.client.openai.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Stream;

import io.micronaut.json.JsonMapper;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Deserializer;
import io.micronaut.serde.exceptions.SerdeException;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import org.owpk.llm.client.ClientException;
import org.owpk.llm.client.openai.model.OpenAIChatRequest;
import org.owpk.llm.client.openai.model.OpenAIChatResponse;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.StreamingHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.client.sse.SseClient;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.serde.Serde;
import io.micronaut.serde.SerdeRegistry;
import io.netty.buffer.ByteBuf;
import reactor.core.publisher.Flux;

public class OpenAiClientImpl implements OpenAiClient {
    private final StreamingHttpClient httpClient;
    private final JsonMapper mapper;
    private final String baseUrl;
    private final String apiKey;

    public OpenAiClientImpl(StreamingHttpClient httpClient, JsonMapper mappr,
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
                .flatMap(data -> {
                    try {
                        OpenAIChatResponse response = mapper.readValue(data, OpenAIChatResponse.class);
                        if (response.getChoices() != null &&
                                !response.getChoices().isEmpty() &&
                                response.getChoices().get(0).getDelta() != null &&
                                response.getChoices().get(0).getDelta().getContent() != null) {
                            return Flux.just(response.getChoices().get(0).getDelta().getContent());
                        }
                    } catch (Exception ignored) {
                    }
                    return Flux.empty(); // ничего не публикуем вместо null
                });
    }

    // Можно добавить другие методы для поддержки дополнительных эндпоинтов OpenAI
}
