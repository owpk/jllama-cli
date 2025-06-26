package org.owpk.llm.client.openai.model;

import java.util.List;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Data;

@Data
@Introspected
@ReflectiveAccess
@SerdeImport(OpenAIChatResponse.class)
public class OpenAIChatResponse {
    private List<Choice> choices;
    private String id;
    private String object;
    private long created;
    private String model;
    private Usage usage;

    @Data
    @Introspected
    @ReflectiveAccess
    @SerdeImport(Choice.class)
    public static class Choice {
        private int index;
        private Delta delta;
        private String finishReason;
    }

    @Data
    @Introspected
    @ReflectiveAccess
    @SerdeImport(Delta.class)
    public static class Delta {
        private String content;
        private String role;
    }

    @Data
    @Introspected
    @ReflectiveAccess
    @SerdeImport(Usage.class)
    public static class Usage {
        private int promptTokens;
        private int completionTokens;
        private int totalTokens;
    }
}