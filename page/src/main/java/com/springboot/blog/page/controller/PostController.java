package com.springboot.blog.page.controller;

import com.springboot.blog.page.dto.PostDTO;
import com.springboot.blog.page.dto.PostResponse;
import com.springboot.blog.page.model.Post;
import com.springboot.blog.page.service.PostService;
import com.springboot.blog.page.utils.AppConstants;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.springboot.blog.page.utils.AppConstants.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;
    private ModelMapper mapper;

    public PostController(ModelMapper mapper, PostService postService) {
        this.mapper = mapper;
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO dto){
        Post post = postService.createPost(dto);
        PostDTO responseDto = convertPostToPostDTO(post);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sorDir", defaultValue = DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id){
        Post post = postService.getPostById(id);
        PostDTO postDto = convertPostToPostDTO(post);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/category/{category_id}")
    public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable long category_id){
        List<PostDTO> posts = postService.getPostByCategory(category_id);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO dto, @PathVariable long id){
        Post post = postService.updatePost(dto, id);
        PostDTO responseDto = convertPostToPostDTO(post);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable long id){
        postService.delete(id);
        return "Post deleted successfully";
    }

    private PostDTO convertPostToPostDTO(Post post){
        PostDTO postDTO = mapper.map(post, PostDTO.class);

//        responseDto.setId(post.getId());
//        responseDto.setTitle(post.getTitle());
//        responseDto.setDescription(post.getDescription());
//        responseDto.setContent(post.getContent());

        return postDTO;
    }
}