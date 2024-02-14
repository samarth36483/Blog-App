package com.springboot.blog.page.service;

import com.springboot.blog.page.dto.PostDTO;
import com.springboot.blog.page.exception.ResourceNotFoundException;
import com.springboot.blog.page.model.Post;
import com.springboot.blog.page.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    PostRepository postRepository;

    @Override
    public Post createPost(PostDTO dto) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setContent(dto.getContent());
        postRepository.save(post);
        return post;
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    @Override
    public Post getPostById(long id) {
        Optional<Post> Optpost = postRepository.findById(id);
        if(Optpost.isEmpty())
            throw new ResourceNotFoundException("No resource found");

        Post post = Optpost.get();
        return post;
    }

    @Override
    public Post updatePost(PostDTO dto, long id) {
        Post post = getPostById(id);
        if(dto.getTitle() != null){
            post.setTitle(dto.getTitle());
        }
        if(dto.getDescription() != null){
            post.setDescription(dto.getDescription());
        }
        if(dto.getContent() != null){
            post.setContent(dto.getContent());
        }
        postRepository.save(post);

        return post;
    }

    @Override
    public void delete(long id) {
        postRepository.deleteById(id);
    }
}
