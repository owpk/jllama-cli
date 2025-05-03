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
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class YamlDialogRepositoryImpl implements DialogRepository {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");
	private static final String EXT = ".yaml";

	private final Storage storage;
	private final PropertiesManager propertiesManager;
	private final ComplexSerializer serializer;

	@Override
	public Mono<String> createNewDialog() {
		return createDialog(true);
	}

	@Override
	public Mono<String> createOrGetDefaultDialog() {
		return createDialog(false);
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
				if (content == null || content.length == 0)
					return List.<Message>of();
				return serializer.convert(content, new TypeReference<List<Message>>() {
				});
			} catch (Exception e) {
				throw new RuntimeException("Failed to read messages for dialog " + dialogId, e);
			}
		}).flatMapMany(it -> Flux.fromIterable(it));
	}

	private String generateChatName() {
		String timestamp = LocalDateTime.now().format(FORMATTER);
		return String.format("%s", timestamp);
	}

	private Mono<String> createDialog(boolean isNew) {
		var properties = propertiesManager.getApplicationProperties();
		if (!isNew)
			if (properties.getCurrentChatId() != null && !properties.getCurrentChatId().isBlank())
				return Mono.just(properties.getCurrentChatId());

		String dialogId = generateChatName();
		return Mono.just(dialogId)
				.doOnNext(id -> {
					var dialogPath = storage.createFileOrDirIfNotExists(
							false, properties.getChatsPath(), id + EXT);
					log.info("Used dialog: " + dialogPath);
					properties.setCurrentChatId(id);
					propertiesManager.getApplicationPropertiesProvider()
							.onPropertiesChanged(properties);
				});
	}
}
