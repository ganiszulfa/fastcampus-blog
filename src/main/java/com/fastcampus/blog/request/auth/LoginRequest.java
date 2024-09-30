package com.fastcampus.blog.request.auth;

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
public class LoginRequest {
    @Size(min = 2, max = 100)
    @NotNull
    private String username;

    @Size(min = 2, max = 100)
    @NotNull
    private String password;

}
