package com.springboot.blog.page;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App Rest APIs",
				description = "Spring Boot Blog App Rest APIs documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Samarth Raipuriya",
						email = "abc@gmail.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Blog App Rest APIs documentation",
				url = "https://github.com/samarth36483/Blog-App.git"
		)
)
public class BlogPageApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogPageApplication.class, args);
	}

}