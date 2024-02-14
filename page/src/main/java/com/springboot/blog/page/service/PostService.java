package com.springboot.blog.page.service;

import com.springboot.blog.page.dto.PostDTO;
import com.springboot.blog.page.model.Post;

import java.util.List;

public interface PostService {

    Post createPost(PostDTO dto);
    List<Post> getAllPosts();
    Post getPostById(long id);
    Post updatePost(PostDTO dto, long id);
    void delete(long id);
}
