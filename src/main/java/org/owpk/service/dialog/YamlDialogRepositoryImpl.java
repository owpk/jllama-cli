package org.owpk.service.dialog;

import java.util.List;
import java.util.UUID;

import org.owpk.config.properties.model.ApplicationProperties;
import org.owpk.storage.Storage;
import org.owpk.utils.serializers.SerializingException;
import org.owpk.utils.serializers.YamlObjectMapper;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class YamlDialogRepositoryImpl implements DialogRepository {
	private static final String EXT = ".yaml";

	private final Storage storage;
	private final ApplicationProperties properties;
	private final YamlObjectMapper serializer;

	@Override
	public Mono<String> createDialog() {
		String dialogId = UUID.randomUUID().toString();
		return Mono.just(dialogId)
				.doOnNext(id -> storage.createFileOrDirIfNotExists(false, properties.getChatsPath(), id + ".json"))
				.map(id -> id);
	}

	@Override
	public Mono<Void> saveMessages(String dialogId, List<Message> messages) {
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
		return Mono.fromCallable(() -> {
			if (!storage.exists(properties.getChatsPath(), dialogId + EXT))
				return List.<Message>of();

			byte[] content = storage.getContent(properties.getChatsPath(), dialogId + EXT);
			try {
				return serializer.convert(content, ChatDialog.class).getMessages();
			} catch (SerializingException e) {
				throw new RuntimeException("Failed to read messages for dialog " + dialogId, e);
			}
		}).flatMapMany(it -> Flux.fromIterable(it));
	}
}
