package com.fastcampus.blog.request.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCategoriesRequest {
     private Integer pageNo;
     private Integer limit;
}
