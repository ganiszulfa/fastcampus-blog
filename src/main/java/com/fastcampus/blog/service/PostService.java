package com.fastcampus.blog.service;

import com.fastcampus.blog.entity.Category;
import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.exception.ApiException;
import com.fastcampus.blog.mapper.PostMapper;
import com.fastcampus.blog.repository.CategoryRepository;
import com.fastcampus.blog.repository.PostRepository;
import com.fastcampus.blog.request.post.CreatePostRequest;
import com.fastcampus.blog.request.post.GetPostBySlugRequest;
import com.fastcampus.blog.request.post.GetPostsRequest;
import com.fastcampus.blog.request.post.UpdatePostBySlugRequest;
import com.fastcampus.blog.response.post.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public GetPostResponse getPostBySlug(GetPostBySlugRequest request) {
        Post post = postRepository
                .findFirstBySlug(request.getSlug())
                .orElseThrow(() -> new ApiException("not found", HttpStatus.NOT_FOUND));
        return PostMapper.INSTANCE.mapToGetPostResponse(post);
    }

    @Cacheable(value="PostService.getPostBySlug", key = "{#request.slug, #isDeleted}")
    public GetPostResponse getPostBySlug(GetPostBySlugRequest request, boolean isDeleted) {
        Post post = postRepository
                .findFirstBySlugAndIsDeleted(request.getSlug(), isDeleted)
                .orElseThrow(() -> new ApiException("not found", HttpStatus.NOT_FOUND));
        return PostMapper.INSTANCE.mapToGetPostResponse(post);
    }

    public CreatePostResponse createPost(CreatePostRequest request) {

        Category category = categoryRepository.findById(request.getCategory().getId())
                .orElseThrow(()-> new ApiException("category not found", HttpStatus.NOT_FOUND));

        Post post = PostMapper.INSTANCE.mapToCreatePostResponse(request);
        post.setCommentCount(0L);
        post.setCreatedAt(Instant.now().getEpochSecond());
        post.setCategory(category);

        post = postRepository.save(post);

        return PostMapper.INSTANCE.mapToCreatePostResponse(post);
    }

    @CachePut(value="PostService.getPostBySlug", key = "#request.slug")
    public UpdatePostBySlugResponse updatePostBySlug(String slug, UpdatePostBySlugRequest request) {
        Post post = postRepository.findFirstBySlugAndIsDeleted(slug, false)
                .orElseThrow(()-> new ApiException("post not found", HttpStatus.NOT_FOUND));
        Category category = categoryRepository.findById(request.getCategory().getId())
                .orElseThrow(()-> new ApiException("category not found", HttpStatus.NOT_FOUND));

        post.setTitle(request.getTitle());
        post.setBody(request.getBody());
        post.setSlug(request.getSlug());
        post.setCategory(category);
        postRepository.save(post);

        return PostMapper.INSTANCE.mapToUpdatePostBySlugResponse(post);
    }

    @CacheEvict(value="PostService.getPostBySlug", key = "{#result.slug, false}")
    public DeletePostByIdResponse deletePostById(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ApiException("post not found", HttpStatus.NOT_FOUND));
        post.setDeleted(true);
        postRepository.save(post);
        return DeletePostByIdResponse.builder().id(id).slug(post.getSlug()).build();
    }

    public PublishPostResponse publishPost(Integer id) {
        Post post = postRepository.findByIdAndIsDeleted(id, false).orElseThrow(
                () -> new ApiException("post not found", HttpStatus.NOT_FOUND));
        post.setPublished(true);
        post.setPublishedAt(Instant.now().getEpochSecond());
        postRepository.save(post);
        return PublishPostResponse.builder().publishedAt(post.getPublishedAt()).build();
    }

    @Cacheable(value="PostService.getPostsByRequest", key = "{#request.pageNo, #request.limit}")
    public List<GetPostResponse> getPosts(GetPostsRequest request) {
        List<Post> posts = postRepository.findByIsDeletedOrderByCreatedAtDesc(false);
        List<GetPostResponse> responses = new ArrayList<>();
        posts.forEach(post -> responses.add(PostMapper.INSTANCE.mapToGetPostResponse(post)));
        return responses;
    }

    @Cacheable(value="PostService.getPostsPublic", key = "{#isDeleted, #isPublished, #request.pageNo}")
    public List<GetPostResponse> getPosts(GetPostsRequest request, boolean isDeleted, boolean isPublished) {
        List<Post> posts = postRepository.findByIsDeletedAndIsPublishedOrderByPublishedAtDesc(isDeleted, isPublished);
        List<GetPostResponse> responses = new ArrayList<>();
        posts.forEach(post -> responses.add(PostMapper.INSTANCE.mapToGetPostResponse(post)));
        return responses;
    }
}
