package com.fastcampus.blog.controller;

import com.fastcampus.blog.request.post.GetPostBySlugRequest;
import com.fastcampus.blog.request.post.GetPostsRequest;
import com.fastcampus.blog.response.post.*;
import com.fastcampus.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/posts")
public class PostPublicController {

    @Autowired
    PostService postService;

    @GetMapping("/{slug}")
    public ResponseEntity<GetPostResponse> getPostBySlug(@Valid @PathVariable String slug) {
        GetPostBySlugRequest request = GetPostBySlugRequest.builder().slug(slug).build();
        return ResponseEntity.ok(postService.getPostBySlug(request, false));
    }

    @GetMapping
    public ResponseEntity<List<GetPostResponse>> getPosts(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                          @RequestParam(required = false, defaultValue = "10") Integer limit) {
        GetPostsRequest request = GetPostsRequest.builder()
                .pageNo(pageNo)
                .limit(limit)
                .build();
        return ResponseEntity.ok(postService.getPosts(request, false, true));
    }

}
