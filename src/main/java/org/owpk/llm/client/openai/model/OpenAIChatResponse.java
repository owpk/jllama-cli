package org.owpk.llm.client.openai.model;

import lombok.Data;
import java.util.List;

@Data
public class OpenAIChatResponse {
    private List<Choice> choices;
    private String id;
    private String object;
    private long created;
    private String model;
    private Usage usage;

    @Data
    public static class Choice {
        private int index;
        private Delta delta;
        private String finishReason;
    }

    @Data
    public static class Delta {
        private String content;
        private String role;
    }

    @Data
    public static class Usage {
        private int promptTokens;
        private int completionTokens;
        private int totalTokens;
    }
}