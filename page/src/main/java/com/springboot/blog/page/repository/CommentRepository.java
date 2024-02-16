package com.springboot.blog.page.repository;

import com.springboot.blog.page.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(long post_id);
}
