package com.springboot.blog.page.service;

import com.springboot.blog.page.dto.CommentDTO;
import com.springboot.blog.page.exception.CommentNotFoundWithPostException;
import com.springboot.blog.page.exception.ResourceNotFoundException;
import com.springboot.blog.page.model.Comment;
import com.springboot.blog.page.model.Post;
import com.springboot.blog.page.repository.CommentRepository;
import com.springboot.blog.page.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;
    private PostService postService;
    private ModelMapper mapper;

    public CommentServiceImpl(ModelMapper mapper, CommentRepository commentRepository, PostService postService) {
        this.mapper = mapper;
        this.commentRepository = commentRepository;
        this.postService = postService;
    }

    @Override
    public Comment createComment(long post_id, CommentDTO commentDTO) {
        Post post = postService.getPostById(post_id);
        Comment comment = new Comment();
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(long post_id, long comment_id) {
        Post post = postService.getPostById(post_id);
        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new ResourceNotFoundException("No comment found with given id"));

        if(comment.getPost().getId() == post.getId()){
            return comment;
        }
        throw new CommentNotFoundWithPostException("No comment found with given id for post");
    }

    @Override
    public List<CommentDTO> getAllComments(long post_id) {
        // retrieve list of comments from database
        List<Comment> comments = commentRepository.findByPostId(post_id);
        // convert list of comment to list of commentDTO
        return comments.stream().map(comment -> convertFromCommentToCommentdto(comment)).collect(Collectors.toList());
    }

    @Override
    public Comment updateComment(long post_id, long comment_id, CommentDTO commentDTO) {
        Post post = postService.getPostById(post_id);
        Comment comment = getCommentById(post_id, comment_id);
        if(comment.getPost().getId() != post.getId()){
            throw new CommentNotFoundWithPostException("Comment does not belong to the post");
        }
        if(commentDTO.getName() != null){
            comment.setName(commentDTO.getName());
        }
        if(commentDTO.getEmail() != null){
            comment.setEmail(commentDTO.getEmail());
        }
        if(commentDTO.getBody() != null){
            comment.setBody(commentDTO.getBody());
        }

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(long post_id, long comment_id) {
        Post post = postService.getPostById(post_id);
        Comment comment = getCommentById(post_id, comment_id);

        if(comment.getPost().getId() != post.getId()){
            throw new CommentNotFoundWithPostException("Comment does not belong to the post");
        }
        commentRepository.delete(comment);
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
