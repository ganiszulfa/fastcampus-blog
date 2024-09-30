package com.fastcampus.blog.controller;

import com.fastcampus.blog.entity.Comment;
import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.repository.CommentRepository;
import com.fastcampus.blog.repository.PostRepository;
import com.fastcampus.blog.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryAdminControllerIntegrationTests {

    private final String HEADER_NAME = "Authorization";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    JwtService jwtService;

    @BeforeEach
    void beforeEach() {
        postRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    void getCategories_givenValid_ShouldReturnOk() throws Exception {
        String jwtToken = jwtService.generateTokenByUsername("ganis");
        mockMvc.perform(get("/api/admin/categories")
                        .header(HEADER_NAME, "Bearer %s".formatted(jwtToken)))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        []
                        """, true));
    }

    @Test
    void getCategoryById_givenValid_ShouldReturnOk() throws Exception {
        String jwtToken = jwtService.generateTokenByUsername("ganis");
        mockMvc.perform(get("/api/admin/categories/1")
                        .header(HEADER_NAME, "Bearer %s".formatted(jwtToken)))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        []
                        """, true));
    }

    @Test
    void createCategory_givenValid_ShouldReturnCreated() throws Exception {
        String jwtToken = jwtService.generateTokenByUsername("ganis");
        mockMvc.perform(post("/api/admin/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name": "Tutorial",
                                "slug": "tutorial"
                                }
                                """)
                        .header(HEADER_NAME, "Bearer %s".formatted(jwtToken)))
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                               {
                                "id": 1,
                                "name": "Tutorial",
                                "slug": "tutorial"
                               }
                        """, true));
    }

    @Test
    void updateCategory_givenValid_ShouldReturnOk() throws Exception {
        String jwtToken = jwtService.generateTokenByUsername("ganis");
        mockMvc.perform(put("/api/admin/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name": "New Name",
                                "slug": "new-name"
                                }
                                """)
                        .header(HEADER_NAME, "Bearer %s".formatted(jwtToken)))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                       {
                       "name": "New Name",
                       "slug": "new-name"
                       }
                       """, true));
    }

    @Test
    void deleteCategoryById_givenValid_ShouldReturnOk() throws Exception {
        String jwtToken = jwtService.generateTokenByUsername("ganis");
        mockMvc.perform(delete("/api/admin/categories/1")
                        .header(HEADER_NAME, "Bearer %s".formatted(jwtToken)))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                       {
                       "id": 1,
                       }
                       """, true));
    }

}
