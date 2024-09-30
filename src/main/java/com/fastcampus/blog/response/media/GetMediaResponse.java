package com.fastcampus.blog.response.media;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMediaResponse {
    private Integer id;
    private String name;
    private String path;
}
