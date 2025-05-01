package org.owpk.service.role;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Role {
	private final String id;
	private final String prompt;
	private final String description;
	private final String expected;

	public Role(String id, String prompt, String description, String expected) {
		this.id = id;
		this.prompt = prompt;
		this.description = description;
		this.expected = expected;
	}

	public Role(String id, String prompt) {
		this(id, prompt, "", "");
	}

	public String getPrompt() {
		return prompt + (expected == null || expected.isEmpty() ? "" : "\nexpected: " + expected);
	}
}
