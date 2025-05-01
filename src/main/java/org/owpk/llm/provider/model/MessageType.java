package org.owpk.llm.provider.model;

import lombok.Getter;

public enum MessageType {
	ASSISTANT("assistant"),
	USER("user"),
	SYSTEM("system");

	@Getter
	private final String type;

	MessageType(String type) {
		this.type = type;
	}

	public static MessageType of(String role) {
		for (MessageType messageType : MessageType.values()) {
			if (messageType.getType().equalsIgnoreCase(role)) {
				return messageType;
			}
		}
		return USER;
	}
}
