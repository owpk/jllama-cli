package org.owpk.client.model;

import java.util.List;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class OllamaEmbeddingResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Embedding> data;
    private Usage usage;

    public OllamaEmbeddingResponse() {
    }

    public OllamaEmbeddingResponse(String id, String object, long created, String model, List<Embedding> data,
            Usage usage) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.data = data;
        this.usage = usage;
    }

    public static class Embedding {
        private String object;
        private List<Double> embedding;
        private int index;

        public Embedding(String object, List<Double> embedding, int index) {
            this.object = object;
            this.embedding = embedding;
            this.index = index;
        }

        public Embedding() {
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public List<Double> getEmbedding() {
            return embedding;
        }

        public void setEmbedding(List<Double> embedding) {
            this.embedding = embedding;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static class Usage {
        private int promptTokens;
        private int totalTokens;

        public Usage() {

        }

        public Usage(int promptTokens, int totalTokens) {
            this.promptTokens = promptTokens;
            this.totalTokens = totalTokens;
        }

        public int getPromptTokens() {
            return promptTokens;
        }

        public void setPromptTokens(int promptTokens) {
            this.promptTokens = promptTokens;
        }

        public int getTotalTokens() {
            return totalTokens;
        }

        public void setTotalTokens(int totalTokens) {
            this.totalTokens = totalTokens;
        }
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Embedding> getData() {
        return data;
    }

    public void setData(List<Embedding> data) {
        this.data = data;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

}