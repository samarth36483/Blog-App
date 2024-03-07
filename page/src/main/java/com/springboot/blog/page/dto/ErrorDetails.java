package com.springboot.blog.page.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Schema(
        description = "Error response object"
)
public class ErrorDetails {

    private Date timestamp;
    private String message;
    private String details;
}
