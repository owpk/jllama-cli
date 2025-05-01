package org.owpk.service.dialog;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Introspected
@ReflectiveAccess
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {
	private String id;
	private String userPrompt;
	private String assistantPrompt;
	private String systemPrompt;
}
