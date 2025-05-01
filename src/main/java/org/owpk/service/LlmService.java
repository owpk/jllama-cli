package org.owpk.service;

import org.owpk.llm.provider.LlmProvider;
import org.owpk.llm.provider.model.ChatRequest;
import org.owpk.llm.provider.model.MessageType;
import org.owpk.service.dialog.DialogRepository;
import org.owpk.service.role.RolesManager;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Data
@RequiredArgsConstructor
public class LlmService {
	private final LlmProvider<?> llmProvider;
	private final DialogRepository dialogRepository;
	private final RolesManager rolesManager;

	/**
	 * Метод для генерации текста с использованием LLM
	 *
	 * @param prompt текстовый запрос
	 * @param roleId идентификатор роли
	 * @return сгенерированный текст
	 */
	public Flux<String> generate(String prompt, String roleId) {
		var rolePrompt = rolesManager.getByRoleId(roleId).getPrompt();
		var messageBuffer = new StringBuilder();
		return llmProvider.generate(prompt, rolePrompt)
				.doOnNext(responses -> {
					// Сохраняем ответ в буфер
					messageBuffer.append(responses);
				}).doOnComplete(() -> {
					System.out.println("Init saving message: " + messageBuffer.toString().length());
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
	}
}