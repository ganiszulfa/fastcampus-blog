package com.fastcampus.blog.controller;

import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostPublicControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Test
    void getPostBySlug_givenValid_shouldReturnOK() throws Exception {

        Post post = new Post();
        post.setSlug("slug1");
        post.setTitle("title1");
        post.setId(1);
        postRepository.save(post);

        mockMvc.perform(get("/api/public/posts/slug1"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id" : 1,
                            "title" : "title1",
                            "slug" : "slug1"
                        }
                        """));
    }

    @Test
    void getPostBySlug_givenPostDoesntExist_shouldReturnNotFound() throws Exception {

        mockMvc.perform(get("/api/public/posts/slug-invalid"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                            "errorMessages" : ["not found"]
                        }
                        """));
    }

    @Test
    void createPost_givenInvalid_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/public/posts"))
                .andExpect(status().isMethodNotAllowed());
    }
}
