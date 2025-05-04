package org.owpk.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.owpk.llm.provider.LlmProvider;
import org.owpk.llm.provider.model.ChatRequest;
import org.owpk.llm.provider.model.MessageType;
import org.owpk.service.dialog.DialogRepository;
import org.owpk.service.dialog.Message;
import org.owpk.service.role.RolesManager;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@RequiredArgsConstructor
@Slf4j
public class LlmService {
	private final LlmProvider<?> llmProvider;
	private final DialogRepository dialogRepository;
	private final RolesManager rolesManager;

	/**
	 * Method for generating text using LLM
	 *
	 * @param prompt text prompt
	 * @param roleId role ID
	 * @return text response
	 */
	public Flux<String> generate(String prompt, String roleId) {
		var rolePrompt = rolesManager.getByRoleId(roleId).getPrompt();
		return llmProvider.generate(prompt, rolePrompt);
	}

	/**
	 * Method for chatting with LLM
	 *
	 * @param request                  user request
	 * @param chatHistoryContextLength chat history context length
	 * @return text response
	 */
	public Flux<String> chat(String prompt, String roleId, int chatHistoryContextLength) {
		var rolePrompt = rolesManager.getByRoleId(roleId).getPrompt();
		var systemRequest = ChatRequest.builder()
				.message(rolePrompt)
				.role(MessageType.SYSTEM)
				.build();
		var chatRequest = ChatRequest.builder()
				.message(prompt)
				.role(MessageType.USER)
				.build();

		return processLlmResponse(
				dialogId -> prepareAndExecuteChatRequest(dialogId, chatHistoryContextLength, systemRequest,
						chatRequest),
				prompt);
	}

	private Flux<String> prepareAndExecuteChatRequest(
			String dialogId,
			int chatHistoryContextLength,
			ChatRequest systemRequest,
			ChatRequest chatRequest) {
		return dialogRepository.getMessages(dialogId)
				.takeLast(chatHistoryContextLength)
				.map(it -> ChatRequest.builder()
						.message(it.getContent())
						.role(it.getType())
						.build())
				.collectList()
				.map(messages -> {
					List<ChatRequest> orderedMessages = new ArrayList<>();
					orderedMessages.add(systemRequest);
					orderedMessages.addAll(messages);
					orderedMessages.add(chatRequest);
					log.info("Final messages sequence: {}", orderedMessages);
					return orderedMessages;
				})
				.flatMapMany(messages -> llmProvider.chat(messages));
	}

	private Flux<String> processLlmResponse(
			Function<String, Flux<String>> responseProvider,
			String prompt) {
		return dialogRepository.createOrGetDefaultDialog()
				.flatMapMany(dialogId -> {
					Flux<String> responseStream = responseProvider.apply(dialogId).cache();

					Mono<Void> saveOperation = responseStream
							.collectList()
							.map(responses -> String.join("", responses))
							.flatMap(fullResponse -> dialogRepository.saveMessages(dialogId,
									List.of(createMessage(prompt, MessageType.USER),
											createMessage(fullResponse, MessageType.ASSISTANT))))
							.then();

					return responseStream
							.doOnComplete(() -> saveOperation.subscribe());
				});
	}

	private Message createMessage(String content, MessageType type) {
		return Message.builder()
				.content(content)
				.type(type)
				.timestamp(LocalDateTime.now().toString())
				.build();
	}

}
