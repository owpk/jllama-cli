package org.owpk.llm.client.ollama.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Introspected
public class OllamaChatResponse {
    @JsonProperty("model")
    private String model;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("message")
    private OllamaChatMessage message;

    @JsonProperty("done")
    private boolean done;

    @JsonProperty("total_duration")
    private long totalDuration;

    @JsonProperty("load_duration")
    private long loadDuration;

    @JsonProperty("prompt_eval_count")
    private int promptEvalCount;

    @JsonProperty("prompt_eval_duration")
    private long promptEvalDuration;

    @JsonProperty("eval_count")
    private int evalCount;

    @JsonProperty("eval_duration")
    private long evalDuration;
}
