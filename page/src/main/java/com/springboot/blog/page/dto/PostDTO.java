package com.springboot.blog.page.dto;

import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private long id;
    private String title;
    private String description;
    private String content;
    private Set<CommentDTO> comments;
}
