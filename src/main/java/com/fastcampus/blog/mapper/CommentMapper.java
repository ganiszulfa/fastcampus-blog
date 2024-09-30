package com.fastcampus.blog.mapper;

import com.fastcampus.blog.entity.Comment;
import com.fastcampus.blog.request.comment.CreateCommentRequest;
import com.fastcampus.blog.response.comment.CreateCommentResponse;
import com.fastcampus.blog.response.comment.GetCommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment map(CreateCommentRequest commentRequest);

    CreateCommentResponse mapToCreateCommentResponse(Comment comment);
    GetCommentResponse mapToGetCommentResponse(Comment comment);

}
