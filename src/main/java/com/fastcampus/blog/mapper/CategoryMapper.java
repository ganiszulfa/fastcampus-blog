package com.fastcampus.blog.mapper;

import com.fastcampus.blog.entity.Category;
import com.fastcampus.blog.request.category.CreateCategoryRequest;
import com.fastcampus.blog.response.category.CreateCategoryResponse;
import com.fastcampus.blog.response.category.GetCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category map(CreateCategoryRequest request);

    CreateCategoryResponse mapToCreateCategoryResponse(Category category);

    GetCategoryResponse mapToGetCategoryResponse(Category category);
}
