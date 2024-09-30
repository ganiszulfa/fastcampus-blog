package com.fastcampus.blog.request.media;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMediaRequest {
    private Integer limit;
    private Integer pageNo;
}
