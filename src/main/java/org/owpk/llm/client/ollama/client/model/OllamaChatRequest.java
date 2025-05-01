package org.owpk.llm.client.ollama.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

/**
 * Generate the next message in a chat with a provided model. This is a
 * streaming endpoint, so there will be a series of responses. Streaming can be
 * disabled using "stream": false. The final response object will include
 * statistics and additional data from the request.
 */
@Data
@Builder
public class OllamaChatRequest {

    /** Required - the model name */
    @JsonProperty("model")
    private String model;

    /** The messages of the chat, this can be used to keep a chat memory */
    @JsonProperty("messages")
    private List<OllamaChatMessage> messages;

    /** List of tools in JSON for the model to use if supported */
    @JsonProperty("tools")
    private List<Object> tools;

    /** The format to return a response in. Format can be json or a JSON schema */
    @JsonProperty("format")
    private String format;

    /**
     * Additional model parameters listed in the documentation for the Modelfile
     * such as temperature
     */
    @JsonProperty("options")
    private Options options;

    /**
     * If false the response will be returned as a single response object, rather
     * than a stream of objects
     */
    @JsonProperty("stream")
    private Boolean stream;

    /**
     * Controls how long the model will stay loaded into memory following the
     * request (default: 5m)
     */
    @JsonProperty("keep_alive")
    private String keepAlive;

    /** The session ID for the chat */
    @JsonProperty("session_id")
    private String sessionId;

}
