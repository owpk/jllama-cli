package org.owpk.llm.client.ollama.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Introspected
@ReflectiveAccess
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class OllamaChatMessage {

    /**
     * The role of the message, either system, user, assistant, or tool
     */
    @JsonProperty("role")
    private String role;

    /**
     * The content of the message
     */
    @JsonProperty("content")
    private String content;

    /**
     * (optional): a list of images to include in the message (for multimodal models
     * such as llava)
     */
    @JsonProperty("images")
    private String[] images;

    /**
     * (optional): a list of tools in JSON that the model wants to use
     */
    @JsonProperty("tool_calls")
    private String[] toolCalls;

}