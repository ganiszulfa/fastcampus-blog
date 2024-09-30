package com.fastcampus.blog.request.comment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class CreateCommentRequest {
    @Size(min = 2, max = 100)
    @NotNull
    private String name;

    @Size(min = 2, max = 100)
    @Email
    @NotNull
    private String email;

    @NotNull
    private Post post;

    @NotNull
    @Size(min = 2, max = 10_000)
    private String body;

    @NotNull
    private String token;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private String slug;
    }
}
