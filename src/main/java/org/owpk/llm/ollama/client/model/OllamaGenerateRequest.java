package org.owpk.llm.ollama.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micronaut.core.annotation.Introspected;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Generate a response for a given prompt with a provided model. This is a
 * streaming endpoint, so there will be a series of responses. The final
 * response object will include statistics and additional data from the request.
 */
@Introspected
@Data
@Builder
@AllArgsConstructor
public class OllamaGenerateRequest {

    public OllamaGenerateRequest(String model) {
        this.model = model;
    }

    /** The model to generate a response for */
    @JsonProperty("model")
    private String model;

    /** The prompt to generate a response for */
    @JsonProperty("prompt")
    private String prompt;

    /** The text after the model response */
    @Nullable
    @JsonProperty("suffix")
    private String suffix;

    /** A list of base64-encoded images (for multimodal models such as llava) */
    @Nullable
    @JsonProperty("images")
    private List<String> images;

    /** The format to return a response in. Format can be json or a JSON schema */
    @Nullable
    @JsonProperty("format")
    private String format;

    /**
     * Additional model parameters listed in the documentation for the Modelfile
     * such as temperature
     */
    @Nullable
    @JsonProperty("options")
    private Options options;

    /** System message to (overrides what is defined in the Modelfile) */
    @Nullable
    @JsonProperty("system")
    private String system;

    /** The prompt template to use (overrides what is defined in the Modelfile) */
    @Nullable
    @JsonProperty("template")
    private String template;

    /**
     * If false the response will be returned as a single response object, rather
     * than a stream of objects
     */
    @Nullable
    @JsonProperty("stream")
    private Boolean stream;

    /**
     * If true no formatting will be applied to the prompt. You may choose to use
     * the raw parameter if you
     * are specifying a full templated prompt in your request to the API
     */
    @Nullable
    @JsonProperty("raw")
    private Boolean raw;

    /**
     * Controls how long the model will stay loaded into memory following the
     * request (default: 5m)
     */
    @Nullable
    @JsonProperty("keep_alive")
    private String keepAlive;

    /**
     * @deprecated The context parameter returned from a previous request to
     *             /generate,
     *             this can be used to keep a short conversational memory
     */
    @Deprecated
    @Nullable
    @JsonProperty("context")
    private String context;

}