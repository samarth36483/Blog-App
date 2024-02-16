package com.springboot.blog.page.exception;

public class CommentNotFoundWithPostException extends RuntimeException{
    public CommentNotFoundWithPostException(String message) {
        super(message);
    }
}
