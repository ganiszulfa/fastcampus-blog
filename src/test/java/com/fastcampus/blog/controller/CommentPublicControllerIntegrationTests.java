package com.fastcampus.blog.controller;

import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentPublicControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Test
    void createComment_givenValid_shouldReturnCreated() throws Exception {
        Post post = new Post();
        post.setTitle("title11");
        post.setSlug("slug11");
        post.setCommentCount(1L);
        postRepository.save(post);
        mockMvc.perform(
                        post("/api/public/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "name": "name",
                                            "email": "email@gmail.com",
                                            "post": {
                                                "slug" : "slug11"
                                            },
                                            "body": "this is body 6"
                                        }
                                        """)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                                        {
                                            "name": "name",
                                            "email": "email@gmail.com",
                                            "post": {
                                                "slug" : "slug11"
                                            },
                                            "body": "this is body 6"
                                        }
                        """));
    }

}
