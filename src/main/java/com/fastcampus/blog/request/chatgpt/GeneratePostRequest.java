package com.fastcampus.blog.request.chatgpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneratePostRequest {

    private String model;
    private List<Message> messages;
    private double temperature;

    @JsonProperty("max_tokens")
    private Integer maxTokens;
    @JsonProperty("top_p")
    private double topP;
    @JsonProperty("frequency_penalty")
    private double frequencyPenalty;
    @JsonProperty("presence_penalty")
    private double presencePenalty;

    @JsonProperty("response_format")
    private ResponseFormat responseFormat;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {
        private String role;
        private List<MessageContent> content;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageContent {
        private String type;
        private String text;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseFormat {
        private String type;
    }
}
