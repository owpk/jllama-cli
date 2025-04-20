package org.owpk.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class OllamaModelsResponse {

    @JsonProperty("models")
    private List<OllamaModel> models;

    // Конструкторы
    public OllamaModelsResponse() {
    }

    // Геттеры и сеттеры
    public List<OllamaModel> getModels() {
        return models;
    }

    public void setModels(List<OllamaModel> models) {
        this.models = models;
    }
}