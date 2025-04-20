package org.owpk.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class OllamaModel {
    @JsonProperty("name")
    private String name;
    @JsonProperty("sha256")
    private String sha256;
    @JsonProperty("modified")
    private String modified;
    @JsonProperty("size")
    private Long size;

    public OllamaModel() {
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}