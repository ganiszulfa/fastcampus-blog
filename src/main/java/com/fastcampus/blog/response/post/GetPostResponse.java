package com.fastcampus.blog.response.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPostResponse {
    private Integer id;
    private String title;
    private String body;
    private String slug;
    private Category category;
    private Long publishedAt;
    private Long commentCount;
    private boolean published;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Category {
        private Integer id;
        private String name;
        private String slug;
    }
}
