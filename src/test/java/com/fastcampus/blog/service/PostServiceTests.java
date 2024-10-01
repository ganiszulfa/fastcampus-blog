package com.fastcampus.blog.service;

import com.fastcampus.blog.entity.Category;
import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.mapper.PostMapper;
import com.fastcampus.blog.repository.CategoryRepository;
import com.fastcampus.blog.repository.PostRepository;
import com.fastcampus.blog.request.post.CreatePostRequest;
import com.fastcampus.blog.response.post.CreatePostResponse;
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

import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PostServiceTests {

    @Autowired
    @InjectMocks
    PostService postService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    PostRepository postRepository;

    private AutoCloseable mocks;

    @BeforeEach
    void beforeEach() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void afterEach() throws Exception {
        mocks.close();
    }

    @Test
    void createPost_givenValid_shouldReturnOk() {
        final var category = new CreatePostRequest.Category();
        category.setId(1);
        CreatePostRequest postRequest = new CreatePostRequest();
        postRequest.setTitle("post title");
        postRequest.setSlug("post slug");
        postRequest.setCategory(category);

        Category cat = new Category();
        cat.setName("new-cat");
        cat.setId(1);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(cat));

        Post post = PostMapper.INSTANCE.mapToCreatePostResponse(postRequest);
        post.setCommentCount(0L);
        post.setCreatedAt(Instant.now().getEpochSecond());
        when(postRepository.save(post)).thenReturn(post);

        CreatePostResponse postResponse = postService.createPost(postRequest);

        Assertions.assertNotNull(postResponse);
        Assertions.assertEquals(postResponse.getCommentCount(), 0);
        Assertions.assertEquals(postResponse.getSlug(), "post slug");

        verify(postRepository, times(1)).save(post);
    }

}
