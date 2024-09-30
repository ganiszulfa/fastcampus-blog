package com.fastcampus.blog.service;

import com.fastcampus.blog.entity.Category;
import com.fastcampus.blog.exception.ApiException;
import com.fastcampus.blog.mapper.CategoryMapper;
import com.fastcampus.blog.repository.CategoryRepository;
import com.fastcampus.blog.repository.PostRepository;
import com.fastcampus.blog.request.category.CreateCategoryRequest;
import com.fastcampus.blog.request.category.GetCategoriesRequest;
import com.fastcampus.blog.request.category.UpdateCategoryRequest;
import com.fastcampus.blog.response.category.CreateCategoryResponse;
import com.fastcampus.blog.response.category.DeleteCategoryResponse;
import com.fastcampus.blog.response.category.GetCategoryResponse;
import com.fastcampus.blog.response.category.UpdateCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PostRepository postRepository;

    public List<GetCategoryResponse> getCategories(GetCategoriesRequest request, boolean isDeleted) {
        List<GetCategoryResponse> getCategoryResponseList = new ArrayList<>();
        Iterable<Category> categories = categoryRepository.findByIsDeleted(isDeleted);
        categories.forEach(category -> getCategoryResponseList.add(
                CategoryMapper.INSTANCE.mapToGetCategoryResponse(category)
        ));
        return getCategoryResponseList;
    }

    public GetCategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ApiException("not found", HttpStatus.NOT_FOUND));
        return CategoryMapper.INSTANCE.mapToGetCategoryResponse(category);
    }

    public List<GetCategoryResponse> getCategories(GetCategoriesRequest request) {
        return getCategories(request, false);
    }

    public CreateCategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = CategoryMapper.INSTANCE.map(request);
        category.setCreatedAt(Instant.now().getEpochSecond());
        categoryRepository.save(category);

        return CategoryMapper.INSTANCE.mapToCreateCategoryResponse(category);
    }

    public UpdateCategoryResponse updateCategory(UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(request.getId())
                .orElseThrow(() -> new ApiException("not found", HttpStatus.NOT_FOUND));
        category.setName(request.getName());
        category.setSlug(request.getSlug());
        categoryRepository.save(category);
        return UpdateCategoryResponse.builder().id(category.getId())
                .name(category.getName()).slug(category.getSlug()).build();
    }

    public DeleteCategoryResponse deleteById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ApiException("not found", HttpStatus.NOT_FOUND));

        Long numberOfPosts = postRepository.countByCategory(category);

        if (numberOfPosts != 0) {
            throw new ApiException("posts still exists in this category", HttpStatus.BAD_REQUEST);
        }
        category.setDeleted(true);
        categoryRepository.save(category);
        return DeleteCategoryResponse.builder().id(category.getId()).build();
    }
}
