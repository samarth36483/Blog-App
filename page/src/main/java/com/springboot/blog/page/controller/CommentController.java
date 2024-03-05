package com.springboot.blog.page.controller;

import com.springboot.blog.page.dto.CommentDTO;
import com.springboot.blog.page.model.Comment;
import com.springboot.blog.page.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "CRUD APIs for Comment resource"
)
public class CommentController {
    private CommentService commentService;
    private ModelMapper mapper;

    public CommentController(CommentService commentService, ModelMapper mapper) {
        this.commentService = commentService;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Create Comment Rest API",
            description = "Create Comment Rest API is used to create new comment into database",
            method = "POST"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status 201 Created"
    )
    @PostMapping("/{post_id}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable long post_id, @Valid @RequestBody CommentDTO commentDTO){
        Comment comment = commentService.createComment(post_id, commentDTO);
        return new ResponseEntity<>(convertFromCommentToCommentdto(comment), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all Comments Rest API",
            description = "Get all Comments Rest API is used to fetch all Comments related to a post from database",
            method = "GET"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    @GetMapping("/{post_id}/comments")
    public List<CommentDTO> getAllComments(@PathVariable long post_id){
        List<CommentDTO> comments = commentService.getAllComments(post_id);

        return comments;
    }

    @Operation(
            summary = "Get Comment by Id Rest API",
            description = "Get Comment by Id Rest API is used to fetch Comment by id from database",
            method = "GET"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    @GetMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable long post_id, @PathVariable long comment_id){
        Comment comment = commentService.getCommentById(post_id, comment_id);
        return new ResponseEntity<>(convertFromCommentToCommentdto(comment), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Comment Rest API",
            description = "Delete Comment Rest API is used to delete Comment from database",
            method = "DELETE"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    @DeleteMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<String> deleteComment(@PathVariable long post_id, @PathVariable long comment_id){
        commentService.deleteComment(post_id, comment_id);
        return new ResponseEntity<>("Comment successfully deleted", HttpStatus.OK);
    }

    @Operation(
            summary = "Update Comment Rest API",
            description = "Update Comment Rest API is used to update Comment in database",
            method = "PUT"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    @PatchMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable long post_id, @PathVariable long comment_id,
                                @Valid @RequestBody CommentDTO commentDTO){
        Comment comment = commentService.updateComment(post_id, comment_id, commentDTO);
        return new ResponseEntity<>(convertFromCommentToCommentdto(comment), HttpStatus.OK);
    }

    private CommentDTO convertFromCommentToCommentdto(Comment comment){
        CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setEmail(comment.getEmail());
//        commentDTO.setBody(comment.getBody());

        return commentDTO;
    }
}