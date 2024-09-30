package com.fastcampus.blog.repository;

import com.fastcampus.blog.entity.Post;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PostRepositoryTests {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void beforeEach() {
        postRepository.deleteAll();
    }

    @AfterEach
    void afterEach() {
        postRepository.deleteAll();
    }

    @Test
    void findByIsDeleted_givenTwoPostsOnlyOneNotDeletedYet_shouldReturnOnlyOne() {
        Post post1 = new Post();
        post1.setDeleted(false);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setDeleted(true);
        postRepository.save(post2);

        List<Post> posts = postRepository.findByIsDeleted(false);
        Assertions.assertNotNull(posts);
        Assertions.assertEquals(posts.size(), 1);
    }

    @Test
    void findByIsDeleted_givenNoPosts_shouldReturnEmpty() {
        List<Post> posts = postRepository.findByIsDeleted(false);
        Assertions.assertNotNull(posts);
        Assertions.assertEquals(posts.size(), 0);
    }
}
