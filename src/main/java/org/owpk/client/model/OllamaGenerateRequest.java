package org.owpk.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class OllamaGenerateRequest {
    private String model;
    private String prompt;
    private Float temperature;
    private Float topP;
    private Integer topK;
    private Integer maxTokens;
    private Boolean stream;

    // Конструкторы
    public OllamaGenerateRequest() {
    }

    public OllamaGenerateRequest(String model, String prompt) {
        this.model = model;
        this.prompt = prompt;
    }

    // Геттеры и сеттеры
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

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    @JsonProperty("top_p")
    public Float getTopP() {
        return topP;
    }

    public void setTopP(Float topP) {
        this.topP = topP;
    }

    @JsonProperty("top_k")
    public Integer getTopK() {
        return topK;
    }

    public void setTopK(Integer topK) {
        this.topK = topK;
    }

    @JsonProperty("max_tokens")
    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    // Строитель для удобного создания запросов
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final OllamaGenerateRequest request = new OllamaGenerateRequest();

        public Builder model(String model) {
            request.setModel(model);
            return this;
        }

        public Builder prompt(String prompt) {
            request.setPrompt(prompt);
            return this;
        }

        public Builder temperature(Float temperature) {
            request.setTemperature(temperature);
            return this;
        }

        public Builder topP(Float topP) {
            request.setTopP(topP);
            return this;
        }

        public Builder topK(Integer topK) {
            request.setTopK(topK);
            return this;
        }

        public Builder maxTokens(Integer maxTokens) {
            request.setMaxTokens(maxTokens);
            return this;
        }

        public Builder stream(Boolean stream) {
            request.setStream(stream);
            return this;
        }

        public OllamaGenerateRequest build() {
            return request;
        }
    }
}