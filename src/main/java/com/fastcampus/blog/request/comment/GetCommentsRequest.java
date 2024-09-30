package com.fastcampus.blog.request.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentsRequest {
     private String postSlug;
     private Integer pageNo;
     private Integer limit;
}
