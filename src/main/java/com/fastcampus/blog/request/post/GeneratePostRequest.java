package com.fastcampus.blog.request.post;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class GeneratePostRequest {

    @Size(min=2, message = "minimal 2 characters")
    @NotNull
    private String title;

    @Min(10)
    @Max(1000)
    @NotNull
    private Integer length;
}
