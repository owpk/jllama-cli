package org.owpk.llm.provider.dialog;

import lombok.Data;

@Data
public class Conversation {
	private String id;
	private String userPrompt;
	private String assistantPrompt;
	private String systemPrompt;
}
