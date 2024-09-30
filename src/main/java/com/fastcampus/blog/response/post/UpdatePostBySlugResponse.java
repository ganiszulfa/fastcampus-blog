package com.fastcampus.blog.response.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostBySlugResponse {
    private String title;
    private String body;
    private String slug;
    private Category category;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Category {
        private Integer id;
    }
}
