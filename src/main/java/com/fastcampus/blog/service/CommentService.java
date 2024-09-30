package com.fastcampus.blog.service;

import com.fastcampus.blog.entity.Comment;
import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.exception.ApiException;
import com.fastcampus.blog.mapper.CommentMapper;
import com.fastcampus.blog.repository.CommentRepository;
import com.fastcampus.blog.repository.PostRepository;
import com.fastcampus.blog.request.comment.CreateCommentRequest;
import com.fastcampus.blog.request.comment.GetCommentByIdRequest;
import com.fastcampus.blog.request.comment.GetCommentsRequest;
import com.fastcampus.blog.response.comment.CreateCommentResponse;
import com.fastcampus.blog.response.comment.GetCommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    public List<GetCommentResponse> getComments(GetCommentsRequest request) {
        Post post = postRepository.findFirstBySlugAndIsDeleted(request.getPostSlug(), false)
                .orElseThrow(() -> new ApiException("post not found", HttpStatus.NOT_FOUND));
        PageRequest pageRequest = PageRequest.of(request.getPageNo(), request.getLimit());
        List<Comment> comments = commentRepository.findByPostId(post.getId(), pageRequest).getContent();
        List<GetCommentResponse> responses = new ArrayList<>();
        comments.forEach(comment -> responses.add(CommentMapper.INSTANCE.mapToGetCommentResponse(comment)));
        return responses;
    }

    public GetCommentResponse getComment(GetCommentByIdRequest request) {
        Comment comment = commentRepository.findById(request.getId())
                .orElseThrow(()-> new ApiException("comment not found", HttpStatus.NOT_FOUND));
        return CommentMapper.INSTANCE.mapToGetCommentResponse(comment);
    }

    @Transactional
    public CreateCommentResponse createComment(CreateCommentRequest request) throws IOException {

        // TODO
        String projectID = "blog-project-434808";
        String recaptchaKey = "todo";
        String token = request.getToken();
        String recaptchaAction = "createComment";
        CreateAssessment.createAssessment(projectID, recaptchaKey, token, recaptchaAction);

        Post post = postRepository.findFirstBySlugAndIsDeleted(request.getPost().getSlug(), false)
                .orElseThrow(()-> new ApiException("post not found", HttpStatus.NOT_FOUND));
        Comment comment = CommentMapper.INSTANCE.map(request);

        comment.setCreatedAt(Instant.now().getEpochSecond());
        comment.getPost().setId(post.getId());
        commentRepository.save(comment);

        post.setCommentCount(post.getCommentCount()+1);
        postRepository.save(post);

        return CommentMapper.INSTANCE.mapToCreateCommentResponse(comment);
    }
}
