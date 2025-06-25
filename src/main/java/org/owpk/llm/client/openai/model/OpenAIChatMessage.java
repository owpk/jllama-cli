package org.owpk.llm.client.openai.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Introspected
@ReflectiveAccess
public class OpenAIChatMessage {
    private String role;
    private String content;
}
