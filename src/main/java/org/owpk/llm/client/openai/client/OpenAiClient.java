package org.owpk.llm.client.openai.client;

import org.owpk.llm.client.openai.model.OpenAIChatRequest;
import org.owpk.llm.client.openai.model.OpenAIChatResponse;
import reactor.core.publisher.Flux;
import io.micronaut.http.annotation.Body;

/**
 * Interface for interacting with OpenAI's REST API
 */
public interface OpenAiClient {
    /**
     * Выполняет чат-запрос к OpenAI
     *
     * @param request запрос на чат-взаимодействие
     * @return Flux с потоком ответов
     */
    Flux<OpenAIChatResponse> chat(@Body OpenAIChatRequest request);
}
