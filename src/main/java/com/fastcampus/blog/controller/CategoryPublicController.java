package com.fastcampus.blog.controller;

import com.fastcampus.blog.request.category.GetCategoriesRequest;
import com.fastcampus.blog.response.category.GetCategoryResponse;
import com.fastcampus.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/categories")
public class CategoryPublicController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<GetCategoryResponse>> getCategories(
            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        GetCategoriesRequest request = GetCategoriesRequest.builder()
                .pageNo(pageNo)
                .limit(limit)
                .build();
        return ResponseEntity.ok(categoryService.getCategories(request, false));
    }

}
