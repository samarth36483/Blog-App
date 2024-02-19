package com.springboot.blog.page.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private long id;
    @NotEmpty(message = "Comment name should not be empty.")
    private String name;
    @NotEmpty(message = "Comment email should not be empty.")
    @Email
    private String email;
    @NotEmpty(message = "Comment body should not be empty.")
    @Size(min = 1, message = "Comment body should be at least 1 characters.")
    private String body;
}
