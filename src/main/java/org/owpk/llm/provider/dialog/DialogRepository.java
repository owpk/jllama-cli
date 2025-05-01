package org.owpk.llm.provider.dialog;

import java.util.List;
import java.util.UUID;

import org.owpk.config.properties.model.ApplicationProperties;
import org.owpk.storage.Storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DialogRepository implements DialogRepo {
	private final Storage storage;
	private final ApplicationProperties properties;
	private final ObjectMapper objectMapper = new ObjectMapper();

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
				String json = objectMapper.writeValueAsString(messages);
				storage.saveContent(properties.getChatsPath() + "/" + dialogId + ".json", json.getBytes(), true);
				return null;
			} catch (JsonProcessingException e) {
				throw new RuntimeException("Failed to save messages for dialog " + dialogId, e);
			}
		});
	}

	@Override
	public Flux<Message> getMessages(String dialogId) {
		return Mono.fromCallable(() -> {
			if (!storage.exists(properties.getChatsPath() + "/" + dialogId + ".json")) {
				return List.<Message>of();
			}
			byte[] content = storage.getContent(properties.getChatsPath() + "/" + dialogId + ".json");
			try {
				return objectMapper.readValue(new String(content),
						objectMapper.getTypeFactory().constructCollectionType(List.class, Message.class));
			} catch (JsonProcessingException e) {
				throw new RuntimeException("Failed to read messages for dialog " + dialogId, e);
			}
		}).flatMapMany(Flux::fromIterable);
	}
}
