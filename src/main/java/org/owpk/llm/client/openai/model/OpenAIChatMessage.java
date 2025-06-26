package org.owpk.llm.client.openai.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Introspected
@ReflectiveAccess
@SerdeImport(OpenAIChatMessage.class)
public class OpenAIChatMessage {
    private String role;
    private String content;
}
