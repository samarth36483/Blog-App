package com.springboot.blog.page.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        description = "Category request and response object"
)
public class CategoryDTO {
    private long id;
    private String name;
    private String description;
}