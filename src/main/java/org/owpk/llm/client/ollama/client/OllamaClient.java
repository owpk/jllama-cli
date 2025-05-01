package org.owpk.llm.client.ollama.client;

import org.owpk.llm.client.ollama.client.model.OllamaChatRequest;
import org.owpk.llm.client.ollama.client.model.OllamaChatResponse;
import org.owpk.llm.client.ollama.client.model.OllamaEmbeddingRequest;
import org.owpk.llm.client.ollama.client.model.OllamaEmbeddingResponse;
import org.owpk.llm.client.ollama.client.model.OllamaGenerateRequest;
import org.owpk.llm.client.ollama.client.model.OllamaGenerateResponse;

import io.micronaut.http.annotation.Body;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Интерфейс для работы с Ollama REST API
 */
public interface OllamaClient {

	/**
	 * Генерирует текст на основе промпта
	 *
	 * @param request запрос на генерацию текста
	 * @return Flux с потоком ответов генерации
	 */
	Flux<OllamaGenerateResponse> generate(@Body OllamaGenerateRequest request);

	/**
	 * Выполняет чат-запрос к модели (поддерживается в более новых версиях Ollama)
	 *
	 * @param request запрос на чат-взаимодействие
	 * @return Flux с потоком ответов
	 */
	Flux<OllamaChatResponse> chat(@Body OllamaChatRequest request);

	/**
	 * Встраивает (embedding) текст с помощью модели
	 *
	 * @param request запрос на создание эмбеддинга
	 * @return Mono с ответом, содержащим вектор эмбеддинга
	 */
	Mono<OllamaEmbeddingResponse> createEmbedding(@Body OllamaEmbeddingRequest request);
}
