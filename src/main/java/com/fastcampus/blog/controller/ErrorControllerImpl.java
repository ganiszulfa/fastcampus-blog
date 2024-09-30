package com.fastcampus.blog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorControllerImpl implements ErrorController {
    @RequestMapping("/error")
    public void handleError(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String exceptionAttribute = "jakarta.servlet.error.exception";
        if (request.getAttribute(exceptionAttribute) != null) {
            throw (Throwable) request.getAttribute(exceptionAttribute);
        }
    }
}