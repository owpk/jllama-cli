package org.owpk.llm.client.openai.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenAIChatMessage {
    private String role;
    private String content;
}
