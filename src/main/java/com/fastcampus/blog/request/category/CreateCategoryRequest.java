package com.fastcampus.blog.request.category;

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
public class CreateCategoryRequest {
    @Size(min = 2, max = 100)
    @NotNull
    private String name;

    @Size(min = 2, max = 100)
    @NotNull
    private String slug;

}
