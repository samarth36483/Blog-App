package com.springboot.blog.page.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private long id;

    // title should not be null or empty
    // title should be at least 3 characters
    @NotEmpty
    @Size(min = 3, message = "Post title should have at least 3 characters.")
    private String title;
    // description should not be null or empty
    // description should be at least 10 characters
    @NotEmpty
    @Size(min = 10, message = "Post description should be at least 10 characters.")
    private String description;
    // content should not be empty
    @NotEmpty
    private String content;
    private Set<CommentDTO> comments;
}
