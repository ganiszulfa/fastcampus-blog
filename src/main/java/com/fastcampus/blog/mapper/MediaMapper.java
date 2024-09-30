package com.fastcampus.blog.mapper;

import com.fastcampus.blog.entity.Media;
import com.fastcampus.blog.response.media.GetMediaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MediaMapper {

    MediaMapper INSTANCE = Mappers.getMapper(MediaMapper.class);

    GetMediaResponse mapToGetMediaResponse(Media media);

    List<GetMediaResponse> mapToGetMediaResponse(List<Media> media);
}
