package com.fastcampus.blog.controller;

import com.fastcampus.blog.exception.ApiException;
import com.fastcampus.blog.request.comment.GetCommentsRequest;
import com.fastcampus.blog.service.CommentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTests {

    private final String HEADER_NAME = "Authorization";

    @Autowired
    @InjectMocks
    CommentPublicController controller;

    @Mock
    CommentService commentService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
    }

    @AfterEach
    void afterEach() throws Exception {
    }

    @Test
    void login_givenInvalidRequest_shouldReturnBadRequest() throws Exception {

        mockMvc.perform(post("/api/public/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "abc"
                                }
                                """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {"errorMessages":["must not be null"]}
                        """, true));

    }

}
