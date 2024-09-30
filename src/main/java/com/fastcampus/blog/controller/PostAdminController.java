package com.fastcampus.blog.controller;

import com.fastcampus.blog.exception.ApiException;
import com.fastcampus.blog.request.post.*;
import com.fastcampus.blog.response.post.*;
import com.fastcampus.blog.service.ChatGPTService;
import com.fastcampus.blog.service.PostService;
import io.github.bucket4j.Bucket;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/posts")
public class PostAdminController {

    @Autowired
    PostService postService;

    @Autowired
    ChatGPTService chatGPTService;

    @Autowired
    Bucket chatGptRequestBucket;

    @GetMapping("/{slug}")
    public ResponseEntity<GetPostResponse> getPostBySlug(@Valid @PathVariable String slug) {
        GetPostBySlugRequest request = GetPostBySlugRequest.builder().slug(slug).build();
        return ResponseEntity.ok(postService.getPostBySlug(request));
    }

    @GetMapping("/generate")
    public ResponseEntity<GeneratePostResponse> generate(@Valid @RequestBody GeneratePostRequest request) {
//        if (!chatGptRequestBucket.tryConsume(1)) {
//            throw new ApiException("too many requests", HttpStatus.TOO_MANY_REQUESTS);
//        }
//
        String body = chatGPTService.generatePostByTitleAndLength(
                request.getTitle(), request.getLength());
        return ResponseEntity.ok(GeneratePostResponse.builder().body(body).build());
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(createPostRequest));
    }

    @GetMapping
    public ResponseEntity<List<GetPostResponse>> getPosts(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                          @RequestParam(required = false, defaultValue = "10") Integer limit) {
        GetPostsRequest request = GetPostsRequest.builder()
                .pageNo(pageNo)
                .limit(limit)
                .build();
        return ResponseEntity.ok(postService.getPosts(request));
    }

    @PutMapping("/{slug}")
    public ResponseEntity<UpdatePostBySlugResponse> updatePostBySlug(@PathVariable String slug,
                                                     @Valid @RequestBody UpdatePostBySlugRequest request) {
        return ResponseEntity.ok(postService.updatePostBySlug(slug, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeletePostByIdResponse> deletePostById(@PathVariable Integer id) {
        return ResponseEntity.ok(postService.deletePostById(id));
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<PublishPostResponse> publishPost(@Valid @PathVariable Integer id) {
        return ResponseEntity.ok(postService.publishPost(id));
    }

}
