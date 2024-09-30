package com.fastcampus.blog.request.post;

import com.fastcampus.blog.response.post.CreatePostResponse;
import jakarta.validation.Valid;
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
public class UpdatePostBySlugRequest {
    @Size(min=2, message = "minimal 2 characters")
    @NotNull
    private String title;
    @Size(min=10, message = "minimal 10 characters")
    @NotNull
    private String body;
    @Size(min=2, message = "minimal 2 characters")
    @NotNull
    private String slug;

    @NotNull
    private Category category;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Category {
        @NotNull
        private Integer id;
    }
}
