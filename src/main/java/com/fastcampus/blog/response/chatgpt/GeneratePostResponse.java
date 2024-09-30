package com.fastcampus.blog.response.chatgpt;

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
public class GeneratePostResponse {

    private String id;
    private String object;
    private String model;
    private Integer created;
    @JsonProperty("system_fingerprint")
    private String systemFingerprint;
    private List<Choice> choices;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Choice {
        private Integer index;
        private Message message;
    }

}
