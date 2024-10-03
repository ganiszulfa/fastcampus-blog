package com.fastcampus.blog.service;

import com.fastcampus.blog.properties.SecretProperties;
import com.fastcampus.blog.request.chatgpt.GeneratePostRequest;
import com.fastcampus.blog.response.chatgpt.GeneratePostResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

@Service
public class ChatGPTService {

    private final RestTemplate restTemplate;

    @Autowired
    SecretProperties secretProperties;

    public ChatGPTService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generatePostByTitleAndLengthFallback(String title, Integer length, Exception e) {
        return title + " bla bla bla" + e.getMessage();
    }

    @CircuitBreaker(name="chatGPTService", fallbackMethod = "generatePostByTitleAndLengthFallback")
    @Cacheable(value="generatePostByTitleAndLength", key="{#title,#length}")
    public String generatePostByTitleAndLength(String title, Integer length) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer %s".formatted(secretProperties.getChatGptKey()));

        final var responseFormat = GeneratePostRequest.ResponseFormat.builder()
                .type("text").build();
        final var content = GeneratePostRequest.MessageContent.builder()
                .text("tuliskan sebuah blog post sebanyak %d kata untuk title: %s. tidak perlu kembalikan titlenya."
                        .formatted(length, title))
                .type("text").build();
        final var message = GeneratePostRequest.Message.builder()
                .role("user")
                .content(new ArrayList<>(Collections.singletonList(content)))
                .build();
        final var request = GeneratePostRequest.builder()
                .model("gpt-4o-mini")
                .temperature(1.0)
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.0)
                .maxTokens(2048)
                .responseFormat(responseFormat)
                .messages(new ArrayList<>(Collections.singletonList(message)))
                .build();

        HttpEntity<GeneratePostRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<GeneratePostResponse> response =
               restTemplate.exchange(
                       secretProperties.getChatGptUrl(),
                       HttpMethod.POST,
                       entity,
                       GeneratePostResponse.class
               );

        return Objects.requireNonNull(response.getBody()).getChoices().getFirst().getMessage().getContent();

    }


}
