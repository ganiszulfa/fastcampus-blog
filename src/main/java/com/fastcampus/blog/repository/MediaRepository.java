package com.fastcampus.blog.repository;

import com.fastcampus.blog.entity.Media;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends CrudRepository<Media, Integer> {
}
