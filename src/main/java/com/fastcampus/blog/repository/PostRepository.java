package com.fastcampus.blog.repository;

import com.fastcampus.blog.entity.Category;
import com.fastcampus.blog.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
    Optional<Post> findFirstBySlugAndIsDeleted(String slug, boolean isDeleted);

    List<Post> findByIsDeleted(boolean isDeleted);

    Optional<Post> findByIdAndIsDeleted(Integer id, boolean isDeleted);

    Optional<Post> findFirstBySlug(String slug);

    Long countByCategory(Category category);

    List<Post> findByIsDeletedAndIsPublishedOrderByPublishedAtDesc(boolean isDeleted, boolean isPublished);

    List<Post> findByIsDeletedOrderByCreatedAtDesc(boolean b);
}
