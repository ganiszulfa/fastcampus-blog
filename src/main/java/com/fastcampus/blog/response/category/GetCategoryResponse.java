package com.fastcampus.blog.response.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCategoryResponse {
    private Integer id;
    private String name;
    private String slug;
}
