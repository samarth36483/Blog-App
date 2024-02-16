package com.springboot.blog.page.service;

import com.springboot.blog.page.dto.CommentDTO;
import com.springboot.blog.page.model.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(long post_id, CommentDTO commentdto);
    Comment getCommentById(long post_id, long id);
    List<CommentDTO> getAllComments(long post_id);
    Comment updateComment(long post_id, long id, CommentDTO commentDTO);
    void deleteComment(long post_id, long id);
}
