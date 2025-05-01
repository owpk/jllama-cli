package org.owpk.llm.client.ollama.client.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OllamaEmbeddingRequest {

    @JsonProperty("model")
    private String model;

    @JsonProperty("prompt")
    private String prompt;

    @JsonProperty("keep_alive")
    private Integer keepAlive;

    // Optional: Some Ollama models support specifying embedding dimensions
    private Integer dimensions;

    // Default constructor for JSON deserialization
    public OllamaEmbeddingRequest() {
    }

    // Constructor with required fields
    public OllamaEmbeddingRequest(String model, String prompt) {
        this.model = model;
        this.prompt = prompt;
    }

    // Full constructor
    public OllamaEmbeddingRequest(String model, String prompt, Integer keepAlive, Integer dimensions) {
        this.model = model;
        this.prompt = prompt;
        this.keepAlive = keepAlive;
        this.dimensions = dimensions;
    }

    // Getters and setters
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

    public Integer getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Integer keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Integer getDimensions() {
        return dimensions;
    }

    public void setDimensions(Integer dimensions) {
        this.dimensions = dimensions;
    }

    // Builder pattern for more flexible object creation
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String model;
        private String prompt;
        private Integer keepAlive;
        private Integer dimensions;

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        public Builder keepAlive(Integer keepAlive) {
            this.keepAlive = keepAlive;
            return this;
        }

        public Builder dimensions(Integer dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        public OllamaEmbeddingRequest build() {
            return new OllamaEmbeddingRequest(model, prompt, keepAlive, dimensions);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OllamaEmbeddingRequest that = (OllamaEmbeddingRequest) o;
        return Objects.equals(model, that.model) &&
                Objects.equals(prompt, that.prompt) &&
                Objects.equals(keepAlive, that.keepAlive) &&
                Objects.equals(dimensions, that.dimensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, prompt, keepAlive, dimensions);
    }

    @Override
    public String toString() {
        return "OllamaEmbeddingRequest{" +
                "model='" + model + '\'' +
                ", prompt='" + prompt + '\'' +
                ", keepAlive=" + keepAlive +
                ", dimensions=" + dimensions +
                '}';
    }
}
