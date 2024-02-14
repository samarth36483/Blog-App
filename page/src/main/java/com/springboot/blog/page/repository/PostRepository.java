package com.springboot.blog.page.repository;

import com.springboot.blog.page.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
