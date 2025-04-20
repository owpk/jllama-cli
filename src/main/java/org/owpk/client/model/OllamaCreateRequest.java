package org.owpk.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class OllamaCreateRequest {

    @JsonProperty("filename")
    private String filename;

    // Конструкторы
    public OllamaCreateRequest() {
    }

    public OllamaCreateRequest(String filename) {
        this.filename = filename;
    }

    // Геттеры и сеттеры
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}