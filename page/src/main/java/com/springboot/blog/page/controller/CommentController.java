package com.springboot.blog.page.controller;

import com.springboot.blog.page.dto.CommentDTO;
import com.springboot.blog.page.model.Comment;
import com.springboot.blog.page.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/posts")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/{post_id}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable long post_id, @RequestBody CommentDTO commentDTO){
        Comment comment = commentService.createComment(post_id, commentDTO);
        return new ResponseEntity<>(convertFromCommentToCommentdto(comment), HttpStatus.CREATED);
    }

    @GetMapping("/{post_id}/comments")
    public List<CommentDTO> getAllComments(@PathVariable long post_id){
        List<CommentDTO> comments = commentService.getAllComments(post_id);

        return comments;
    }

    @GetMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable long post_id, @PathVariable long comment_id){
        Comment comment = commentService.getCommentById(post_id, comment_id);
        return new ResponseEntity<>(convertFromCommentToCommentdto(comment), HttpStatus.OK);
    }

    @DeleteMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<String> deleteComment(@PathVariable long post_id, @PathVariable long comment_id){
        commentService.deleteComment(post_id, comment_id);
        return new ResponseEntity<>("Comment successfully deleted", HttpStatus.OK);
    }

    @PatchMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable long post_id, @PathVariable long comment_id,
                                @RequestBody CommentDTO commentDTO){
        Comment comment = commentService.updateComment(post_id, comment_id, commentDTO);
        return new ResponseEntity<>(convertFromCommentToCommentdto(comment), HttpStatus.OK);
    }

    private CommentDTO convertFromCommentToCommentdto(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setEmail(comment.getEmail());
        commentDTO.setBody(comment.getBody());

        return commentDTO;
    }
}
