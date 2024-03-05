package com.springboot.blog.page.controller;

import com.springboot.blog.page.dto.PostDTO;
import com.springboot.blog.page.dto.PostResponse;
import com.springboot.blog.page.model.Post;
import com.springboot.blog.page.service.PostService;
import com.springboot.blog.page.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "CRUD APIs for Post resource"
)
public class PostController {

    private PostService postService;
    private ModelMapper mapper;

    public PostController(ModelMapper mapper, PostService postService) {
        this.mapper = mapper;
        this.postService = postService;
    }

    @Operation(
            summary = "Create Post Rest API",
            description = "Create Post Rest API is used to create new Post into database",
            method = "POST"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status 201 Created"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO dto){
        Post post = postService.createPost(dto);
        PostDTO responseDto = convertPostToPostDTO(post);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all Posts Rest API",
            description = "Get all Posts Rest API is used to fetch all Posts from database",
            method = "GET"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sorDir", defaultValue = DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @Operation(
            summary = "Get Post by Id Rest API",
            description = "Get Post by Id Rest API is used to fetch Post by id from database",
            method = "GET"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id){
        Post post = postService.getPostById(id);
        PostDTO postDto = convertPostToPostDTO(post);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Post by category Rest API",
            description = "Get Post by category Rest API is used to fetch Post by category from database",
            method = "GET"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    @GetMapping("/category/{category_id}")
    public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable long category_id){
        List<PostDTO> posts = postService.getPostByCategory(category_id);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Post Rest API",
            description = "Update Post Rest API is used to update Post in database",
            method = "PUT"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO dto, @PathVariable long id){
        Post post = postService.updatePost(dto, id);
        PostDTO responseDto = convertPostToPostDTO(post);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Post Rest API",
            description = "Delete Post Rest API is used to delete Post from database",
            method = "DELETE"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
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