package com.springboot.blog.page.repository;

import com.springboot.blog.page.dto.PostDTO;
import com.springboot.blog.page.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCategoryId(long category_id);
}
