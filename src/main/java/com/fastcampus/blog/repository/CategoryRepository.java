package com.fastcampus.blog.repository;

import com.fastcampus.blog.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    Iterable<Category> findByIsDeleted(boolean isDeleted);
}
