package com.fastcampus.blog.controller;

import com.fastcampus.blog.request.comment.CreateCommentRequest;
import com.fastcampus.blog.request.comment.GetCommentByIdRequest;
import com.fastcampus.blog.request.comment.GetCommentsRequest;
import com.fastcampus.blog.response.comment.CreateCommentResponse;
import com.fastcampus.blog.response.comment.GetCommentResponse;
import com.fastcampus.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/comments")
public class CommentAdminController {

    @Autowired
    CommentService commentService;

    @GetMapping
    public ResponseEntity<List<GetCommentResponse>> getComments(@RequestParam(required = false) String postSlug,
                                                               @RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                                               @RequestParam(required = false, defaultValue = "10") Integer limit) {
        GetCommentsRequest request = GetCommentsRequest.builder()
                .postSlug(postSlug)
                .pageNo(pageNo)
                .limit(limit)
                .build();
        return ResponseEntity.ok(commentService.getComments(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCommentResponse> getComment(@PathVariable Integer id) {
        GetCommentByIdRequest request = GetCommentByIdRequest.builder().id(id).build();
        return ResponseEntity.ok(commentService.getComment(request));
    }

    @PostMapping
    public ResponseEntity<CreateCommentResponse> createComment(@Valid @RequestBody CreateCommentRequest comment) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(comment));
    }

}
