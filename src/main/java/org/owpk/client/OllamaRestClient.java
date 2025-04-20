package org.owpk.client;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.client.annotation.Client;
import java.util.Map;
import org.owpk.client.model.OllamaChatRequest;
import org.owpk.client.model.OllamaChatResponse;
import org.owpk.client.model.OllamaCreateRequest;
import org.owpk.client.model.OllamaEmbeddingRequest;
import org.owpk.client.model.OllamaEmbeddingResponse;
import org.owpk.client.model.OllamaGenerateRequest;
import org.owpk.client.model.OllamaGenerateResponse;
import org.owpk.client.model.OllamaModelRequest;
import org.owpk.client.model.OllamaModelsResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Интерфейс для работы с Ollama REST API
 * Методы интерфейса соответствуют эндпоинтам Ollama API
 */
@Client("${ollama.api.url:`http://localhost:11434`}")
public interface OllamaRestClient {
    /**
     * Загружает модель из библиотеки Ollama
     *
     * @param request запрос на загрузку модели
     * @return Mono с ответом о статусе загрузки
     */
    @Post("/api/pull")
    @Consumes(MediaType.APPLICATION_JSON)
    Mono<Map<String, Object>> pullModel(@Body OllamaModelRequest request);

    /**
     * Генерирует текст на основе промпта
     *
     * @param request запрос на генерацию текста
     * @return Flux с потоком ответов генерации
     */
    @Post("/api/generate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON_STREAM)
    Flux<OllamaGenerateResponse> generate(@Body OllamaGenerateRequest request);

    /**
     * Получает список установленных моделей
     *
     * @return Mono со списком моделей
     */
    @Get("/api/models")
    @Produces(MediaType.APPLICATION_JSON)
    Mono<OllamaModelsResponse> listModels();

    /**
     * Создает локальную модель из файла
     *
     * @param request запрос на создание модели с указанием пути к файлу
     * @return Mono с информацией о созданной модели
     */
    @Post("/api/create")
    @Consumes(MediaType.APPLICATION_JSON)
    Mono<Map<String, Object>> createModel(@Body OllamaCreateRequest request);

    /**
     * Удаляет установленную модель
     *
     * @param modelName имя модели для удаления
     * @return Mono<Void> завершающийся после успешного удаления
     */
    @Delete("/api/models/{modelName}")
    Mono<Void> deleteModel(@PathVariable String modelName);

    /**
     * Выполняет чат-запрос к модели (поддерживается в более новых версиях Ollama)
     *
     * @param request запрос на чат-взаимодействие
     * @return Flux с потоком ответов
     */
    @Post("/api/chat")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON_STREAM)
    Flux<OllamaChatResponse> chat(@Body OllamaChatRequest request);

    /**
     * Встраивает (embedding) текст с помощью модели
     *
     * @param request запрос на создание эмбеддинга
     * @return Mono с ответом, содержащим вектор эмбеддинга
     */
    @Post("/api/embeddings")
    @Consumes(MediaType.APPLICATION_JSON)
    Mono<OllamaEmbeddingResponse> createEmbedding(
        @Body OllamaEmbeddingRequest request
    );
}
