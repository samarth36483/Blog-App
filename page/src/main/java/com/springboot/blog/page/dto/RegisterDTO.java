package com.springboot.blog.page.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "Register request DTO"
)
public class RegisterDTO {

    private String name;
    private String email;
    private String username;
    private String password;
}