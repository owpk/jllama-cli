package org.owpk.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OllamaChatResponse {
    private String model;
    private String created_at;
    private Message message;
    private boolean done;

    @JsonProperty("total_duration")
    private long totalDuration;

    @JsonProperty("load_duration")
    private long loadDuration;

    @JsonProperty("prompt_eval_count")
    private int promptEvalCount;

    @JsonProperty("prompt_eval_duration")
    private long promptEvalDuration;

    @JsonProperty("eval_count")
    private int evalCount;

    @JsonProperty("eval_duration")
    private long evalDuration;

    private List<Integer> context;

    public OllamaChatResponse() {
    }

    // Nested Message class to represent the message structure
    public static class Message {
        private String role;
        private String content;

        // Getters and Setters
        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "Message{" +
                    "role='" + role + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    // Getters and Setters
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(long totalDuration) {
        this.totalDuration = totalDuration;
    }

    public long getLoadDuration() {
        return loadDuration;
    }

    public void setLoadDuration(long loadDuration) {
        this.loadDuration = loadDuration;
    }

    public int getPromptEvalCount() {
        return promptEvalCount;
    }

    public void setPromptEvalCount(int promptEvalCount) {
        this.promptEvalCount = promptEvalCount;
    }

    public long getPromptEvalDuration() {
        return promptEvalDuration;
    }

    public void setPromptEvalDuration(long promptEvalDuration) {
        this.promptEvalDuration = promptEvalDuration;
    }

    public int getEvalCount() {
        return evalCount;
    }

    public void setEvalCount(int evalCount) {
        this.evalCount = evalCount;
    }

    public long getEvalDuration() {
        return evalDuration;
    }

    public void setEvalDuration(long evalDuration) {
        this.evalDuration = evalDuration;
    }

    public List<Integer> getContext() {
        return context;
    }

    public void setContext(List<Integer> context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "OllamaChatResponse{" +
                "model='" + model + '\'' +
                ", created_at='" + created_at + '\'' +
                ", message=" + message +
                ", done=" + done +
                ", totalDuration=" + totalDuration +
                ", loadDuration=" + loadDuration +
                ", promptEvalCount=" + promptEvalCount +
                ", promptEvalDuration=" + promptEvalDuration +
                ", evalCount=" + evalCount +
                ", evalDuration=" + evalDuration +
                ", context=" + context +
                '}';
    }
}
