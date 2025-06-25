package org.owpk.llm.client.ollama.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Introspected
@ReflectiveAccess
@NoArgsConstructor
public class OllamaEmbeddingResponse {
    @JsonProperty("embedding")
    private double[] embedding;
}