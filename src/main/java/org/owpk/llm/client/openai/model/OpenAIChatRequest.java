package org.owpk.llm.client.openai.model;

import java.util.List;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Introspected
@ReflectiveAccess
@SerdeImport(OpenAIChatRequest.class)
public class OpenAIChatRequest {
    private String model;
    private List<OpenAIChatMessage> messages;
    private boolean stream;
    private Double temperature;
}