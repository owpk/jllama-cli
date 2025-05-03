package org.owpk.llm.provider.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRequest {
	private String message;
	private String role;

	public static class ChatRequestBuilder {
		public ChatRequestBuilder role(MessageType type) {
			this.role = type.getType().toLowerCase();
			return this;
		}

		public ChatRequestBuilder role(String role) {
			this.role = role.toLowerCase();
			return this;
		}
	}

	@JsonGetter
	public String getRole() {
		return role;
	}

	@JsonSetter
	public void setRole(String role) {
		this.role = role.toLowerCase();
	}
}
