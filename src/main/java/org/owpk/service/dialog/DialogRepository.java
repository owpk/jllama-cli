package org.owpk.service.dialog;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DialogRepository {

	/**
	 * Создает новый диалог
	 * 
	 * @return ID созданного диалога
	 */
	Mono<String> createDialog();

	/**
	 * Сохраняет сообщения для указанного диалога
	 * 
	 * @param dialogId ID диалога
	 * @param messages список сообщений для сохранения
	 */
	Mono<Void> saveMessages(String dialogId, List<Message> messages);

	/**
	 * Загружает все сообщения диалога
	 * 
	 * @param dialogId ID диалога
	 * @return поток сообщений диалога
	 */
	Flux<Message> getMessages(String dialogId);

	/**
	 * Получает список всех доступных диалогов
	 * 
	 * @return поток метаданных диалогов
	 */
	// Flux<DialogMetadata> listDialogs(); TODO implement later
}
