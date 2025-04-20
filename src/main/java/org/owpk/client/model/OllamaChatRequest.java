package org.owpk.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OllamaChatRequest {

    @JsonProperty("model")
    private String model;

    @JsonProperty("prompt")
    private String prompt;

    @JsonProperty("session_id")
    private String sessionId;

    public OllamaChatRequest() {}

    public OllamaChatRequest(String model, String prompt, String sessionId) {
        this.model = model;
        this.prompt = prompt;
        this.sessionId = sessionId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
