package org.owpk.service;

import org.owpk.llm.provider.LlmProvider;
import org.owpk.llm.provider.model.ChatRequest;
import org.owpk.llm.provider.model.MessageType;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@RequiredArgsConstructor
public class LlmService {
	private final LlmProvider<?> llmProvider;

	/**
	 * Метод для генерации текста с использованием LLM
	 *
	 * @param prompt текстовый запрос
	 * @param roleId идентификатор роли
	 * @return сгенерированный текст
	 */
	public Flux<String> generate(String prompt, String roleId) {
		return Mono.just(request)
				.flatMap(req -> {
					if (req.getDialogId() == null) {
						// Создаем новый диалог
						return llmProvider.startDialog(req.getRoleId());
					}
					return Mono.just(req.getDialogId());
				})
				.flatMapMany(dialogId -> {
					// Создаем сообщение пользователя
					Message userMessage = Message.builder()
							.id(UUID.randomUUID().toString())
							.dialogId(dialogId)
							.content(request.getMessage())
							.timestamp(LocalDateTime.now())
							.type(MessageType.USER)
							.build();

					// Получаем ответ от LLM
					return llmProvider.chat(request.getMessage(), request.getRoleId())
							.collectList()
							.flatMap(responses -> {
								// Создаем сообщение от ассистента
								Message assistantMessage = Message.builder()
										.id(UUID.randomUUID().toString())
										.dialogId(dialogId)
										.content(String.join("", responses))
										.timestamp(LocalDateTime.now())
										.type(MessageType.ASSISTANT)
										.build();

								// Сохраняем сообщения
								return llmProvider.saveDialog(dialogId,
										Arrays.asList(userMessage, assistantMessage))
										.thenReturn(responses);
							})
							.flatMapMany(Flux::fromIterable);
				});
	}

	/**
	 * Метод для общения с LLM
	 *
	 * @param request                  запрос от пользователя
	 * @param chatHistoryContextLength длина контекста истории чата
	 * @return ответ от LLM
	 */
	public Flux<String> chat(String request, int chatHistoryContextLength) {
		var message = ChatRequest.builder()
				.message(request).role(MessageType.USER);

		throw new UnsupportedOperationException("Unimplemented method 'chat'");
		// return Mono.just(request)
		// .flatMap(req -> {
		// if (req.getDialogId() == null) {
		// // Создаем новый диалог
		// return llmProvider.startDialog(req.getRoleId());
		// }
		// return Mono.just(req.getDialogId());
		// })
		// .flatMapMany(dialogId -> {
		// // Создаем сообщение пользователя
		// Message userMessage = Message.builder()
		// .id(UUID.randomUUID().toString())
		// .dialogId(dialogId)
		// .content(request.getMessage())
		// .timestamp(LocalDateTime.now())
		// .type(MessageType.USER)
		// .build();

		// // Получаем ответ от LLM
		// return llmProvider.chat(request.getMessage(), request.getRoleId())
		// .collectList()
		// .flatMap(responses -> {
		// // Создаем сообщение от ассистента
		// Message assistantMessage = Message.builder()
		// .id(UUID.randomUUID().toString())
		// .dialogId(dialogId)
		// .content(String.join("", responses))
		// .timestamp(LocalDateTime.now())
		// .type(MessageType.ASSISTANT)
		// .build();

		// // Сохраняем сообщения
		// return llmProvider.saveDialog(dialogId,
		// Arrays.asList(userMessage, assistantMessage))
		// .thenReturn(responses);
		// })
		// .flatMapMany(Flux::fromIterable);
		// });
	}
}