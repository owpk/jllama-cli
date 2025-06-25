package org.owpk.llm.client.openai.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class OpenAIChatRequest {
    private String model;
    private List<OpenAIChatMessage> messages;
    private boolean stream;
    private Double temperature;
}