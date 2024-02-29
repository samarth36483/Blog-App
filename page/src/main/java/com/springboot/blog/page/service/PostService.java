package com.springboot.blog.page.service;

import com.springboot.blog.page.dto.PostDTO;
import com.springboot.blog.page.dto.PostResponse;
import com.springboot.blog.page.model.Post;

import java.util.List;

public interface PostService {

    Post createPost(PostDTO dto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    Post getPostById(long id);
    List<PostDTO> getPostByCategory(long category_id);
    Post updatePost(PostDTO dto, long id);
    void delete(long id);
}
