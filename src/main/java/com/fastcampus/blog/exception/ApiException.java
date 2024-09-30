package com.fastcampus.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

  private final String message;
  @Getter
  private final HttpStatus httpStatus;

  public ApiException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
    this.message = message;
  }

    @Override
    public String getMessage() {
        return message;
    }

}
