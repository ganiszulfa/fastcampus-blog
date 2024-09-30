package com.fastcampus.blog.controller;

import com.fastcampus.blog.config.Bucket4jConfig;
import com.fastcampus.blog.exception.ApiException;
import com.fastcampus.blog.request.auth.LoginRequest;
import com.fastcampus.blog.response.auth.LoginResponse;
import com.fastcampus.blog.service.JwtService;
import com.fastcampus.blog.service.MyUserDetailsService;
import io.github.bucket4j.Bucket;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    Bucket4jConfig bucket4jConfig;

    @PostMapping("/api/public/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        Bucket loginBucket = bucket4jConfig.loginBucket(loginRequest.getUsername());
        if (!loginBucket.tryConsume(1)) {
            throw new ApiException("too many requests", HttpStatus.TOO_MANY_REQUESTS);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            String token
                    = jwtService.generateToken
                    (myUserDetailsService.loadUserByUsername(loginRequest.getUsername()));

            return LoginResponse.builder().token(token).build();
        }

        throw new UsernameNotFoundException("invalid user");
    }
}
