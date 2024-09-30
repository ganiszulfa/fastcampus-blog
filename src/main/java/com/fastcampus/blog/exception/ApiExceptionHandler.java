package com.fastcampus.blog.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionResponse> handler(ApiException exception) {
        List<String> errorMessages = new ArrayList<>(Collections.singletonList(exception.getMessage()));
        ApiExceptionResponse response = ApiExceptionResponse.builder().ErrorMessages(errorMessages).build();
        return ResponseEntity.status(exception.getHttpStatus()).body(response);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiExceptionResponse> handler(SQLIntegrityConstraintViolationException exception) {
        List<String> errorMessages = new ArrayList<>(Collections.singletonList(exception.getMessage()));
        ApiExceptionResponse response = ApiExceptionResponse.builder().ErrorMessages(errorMessages).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiExceptionResponse> handler(ExpiredJwtException exception) {
        List<String> errorMessages = new ArrayList<>(Collections.singletonList(exception.getMessage()));
        ApiExceptionResponse response = ApiExceptionResponse.builder().ErrorMessages(errorMessages).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> handler(MethodArgumentNotValidException exception) {
        List<String> errorMessages = new ArrayList<>();
        exception.getFieldErrors().forEach(fieldError -> errorMessages.add(fieldError.getDefaultMessage()));
        ApiExceptionResponse response = ApiExceptionResponse.builder().ErrorMessages(errorMessages).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
