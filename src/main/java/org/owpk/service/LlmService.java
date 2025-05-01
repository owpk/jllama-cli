package org.owpk.service;

import java.time.LocalDateTime;
import java.util.List;

import org.owpk.llm.provider.LlmProvider;
import org.owpk.llm.provider.model.ChatRequest;
import org.owpk.llm.provider.model.MessageType;
import org.owpk.service.dialog.DialogRepository;
import org.owpk.service.dialog.Message;
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

		return dialogRepository.createDialog()
				.flatMapMany(dialogId -> llmProvider.generate(prompt, rolePrompt)
						.doOnNext(responses -> messageBuffer.append(responses))
						.doOnComplete(() -> dialogRepository.saveMessages(dialogId,
								List.of(createMessage(prompt, MessageType.USER),
										createMessage(messageBuffer.toString(), MessageType.ASSISTANT)))
								.subscribe()));
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

	private Message createMessage(String content, MessageType type) {
		return Message.builder()
				.content(content)
				.type(type)
				.timestamp(LocalDateTime.now())
				.build();
	}

}