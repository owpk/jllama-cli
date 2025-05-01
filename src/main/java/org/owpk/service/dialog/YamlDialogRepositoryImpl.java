package org.owpk.service.dialog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.owpk.config.properties.PropertiesManager;
import org.owpk.storage.Storage;
import org.owpk.utils.serializers.ComplexSerializer;
import org.owpk.utils.serializers.SerializingException;

import com.fasterxml.jackson.core.type.TypeReference;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class YamlDialogRepositoryImpl implements DialogRepository {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");
	private static final String EXT = ".yaml";

	private final Storage storage;
	private final PropertiesManager propertiesManager;
	private final ComplexSerializer serializer;

	@Override
	public Mono<String> createDialog() {
		var properties = propertiesManager.getApplicationProperties();
		if (properties.getCurrentChatId() != null)
			return Mono.just(properties.getCurrentChatId());
		String dialogId = generateChatName();
		return Mono.just(dialogId)
				.doOnNext(id -> {
					storage.createFileOrDirIfNotExists(
							false, properties.getChatsPath(), id + EXT);
					properties.setCurrentChatId(id);
					propertiesManager.getApplicationPropertiesProvider()
							.onPropertiesChanged(properties);
				});
	}

	private String generateChatName() {
		String timestamp = LocalDateTime.now().format(FORMATTER);
		return String.format("%s", timestamp);
	}

	@Override
	public Mono<Void> saveMessages(String dialogId, List<Message> messages) {
		var properties = propertiesManager.getApplicationProperties();
		return Mono.fromCallable(() -> {
			try {
				byte[] data = serializer.dump(messages);
				var dialogFile = storage.createFileOrDirIfNotExists(false, properties.getChatsPath(),
						dialogId + EXT);
				storage.saveContent(dialogFile, data, true);
				return null;
			} catch (SerializingException e) {
				throw new RuntimeException("Failed to save messages for dialog " + dialogId, e);
			}
		});
	}

	@Override
	public Flux<Message> getMessages(String dialogId) {
		var properties = propertiesManager.getApplicationProperties();
		return Mono.fromCallable(() -> {
			if (!storage.exists(properties.getChatsPath(), dialogId + EXT))
				return List.<Message>of();

			byte[] content = storage.getContent(properties.getChatsPath(), dialogId + EXT);
			try {
				return serializer.convert(content, new TypeReference<List<Message>>() {
				});
			} catch (Exception e) {
				throw new RuntimeException("Failed to read messages for dialog " + dialogId, e);
			}
		}).flatMapMany(it -> Flux.fromIterable(it));
	}
}
