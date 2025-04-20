package org.owpk.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class OllamaModelRequest {

    @JsonProperty("model")
    private String model;

    // Конструкторы
    public OllamaModelRequest() {
    }

    public OllamaModelRequest(String model) {
        this.model = model;
    }

    // Геттеры и сеттеры
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}