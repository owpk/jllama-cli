package org.owpk.llm.provider.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRequest {
	private String message;
	private MessageType role;

	@JsonGetter
	public String getRole() {
		return role.getType();
	}

	@JsonSetter
	public void setRole(String role) {
		role = role.toLowerCase();
		this.role = MessageType.of(role);
	}
}
