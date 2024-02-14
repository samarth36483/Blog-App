package com.springboot.blog.page.controller;

import com.springboot.blog.page.dto.PostDTO;
import com.springboot.blog.page.model.Post;
import com.springboot.blog.page.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO dto){
        Post post = postService.createPost(dto);
        PostDTO responseDto = convertPostToPostDTO(post);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<PostDTO> getAllPosts(){
        List<Post> posts = postService.getAllPosts();
        return posts.stream().map(post -> convertPostToPostDTO(post)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id){
        Post post = postService.getPostById(id);
        PostDTO responseDto = convertPostToPostDTO(post);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO dto, @PathVariable long id){
        Post post = postService.updatePost(dto, id);
        PostDTO responseDto = convertPostToPostDTO(post);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable long id){
        postService.delete(id);
        return "Post deleted successfully";
    }

    private PostDTO convertPostToPostDTO(Post post){
        PostDTO responseDto = new PostDTO();
        responseDto.setId(post.getId());
        responseDto.setTitle(post.getTitle());
        responseDto.setDescription(post.getDescription());
        responseDto.setContent(post.getContent());

        return responseDto;
    }
}