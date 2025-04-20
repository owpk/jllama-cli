package org.owpk.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class OllamaGenerateResponse {
    private String model;
    private String response;
    private Long createdAt;
    private Boolean done;
    private OllamaGenerateStats stats;

    public OllamaGenerateResponse() {
    }

    // Геттеры и сеттеры
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @JsonProperty("created_at")
    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public OllamaGenerateStats getStats() {
        return stats;
    }

    public void setStats(OllamaGenerateStats stats) {
        this.stats = stats;
    }
}

@Introspected
class OllamaGenerateStats {
    @JsonProperty("prompt_eval_count")
    private Integer promptEvalCount;

    @JsonProperty("eval_count")
    private Integer evalCount;

    @JsonProperty("eval_duration")
    private Long evalDuration;

    public OllamaGenerateStats() {
    }

    // Геттеры и сеттеры
    public Integer getPromptEvalCount() {
        return promptEvalCount;
    }

    public void setPromptEvalCount(Integer promptEvalCount) {
        this.promptEvalCount = promptEvalCount;
    }

    public Integer getEvalCount() {
        return evalCount;
    }

    public void setEvalCount(Integer evalCount) {
        this.evalCount = evalCount;
    }

    public Long getEvalDuration() {
        return evalDuration;
    }

    public void setEvalDuration(Long evalDuration) {
        this.evalDuration = evalDuration;
    }
}