package org.owpk.llm.client.ollama.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.SerdeImport;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Introspected
@ReflectiveAccess
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@SerdeImport(OllamaGenerateRequest.class)
public class OllamaGenerateRequest {

    @JsonProperty("model")
    private String model;

    @JsonProperty("prompt")
    private String prompt;

    @Nullable
    @JsonProperty("suffix")
    private String suffix;

    @Nullable
    @JsonProperty("images")
    private List<String> images;

    @Nullable
    @JsonProperty("format")
    private String format;

    @Nullable
    @JsonProperty("options")
    private Options options;

    @Nullable
    @JsonProperty("system")
    private String system;

    @Nullable
    @JsonProperty("template")
    private String template;

    @Nullable
    @JsonProperty("stream")
    private Boolean stream;

    @Nullable
    @JsonProperty("raw")
    private Boolean raw;

    @Nullable
    @JsonProperty("keep_alive")
    private String keepAlive;

    @Deprecated
    @Nullable
    @JsonProperty("context")
    private String context;
}