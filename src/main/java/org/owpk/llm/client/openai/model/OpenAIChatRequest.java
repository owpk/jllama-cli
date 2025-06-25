package org.owpk.llm.client.openai.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;

@Data
@Builder
@Introspected
@ReflectiveAccess
public class OpenAIChatRequest {
    private String model;
    private List<OpenAIChatMessage> messages;
    private boolean stream;
    private Double temperature;
}