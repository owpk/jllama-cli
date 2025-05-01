package org.owpk.llm.client.ollama.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Introspected
@NoArgsConstructor
public class OllamaEmbeddingResponse {
    @JsonProperty("embedding")
    private double[] embedding;
}