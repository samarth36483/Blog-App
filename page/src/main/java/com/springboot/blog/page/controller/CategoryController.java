package com.springboot.blog.page.controller;

import com.springboot.blog.page.dto.CategoryDTO;
import com.springboot.blog.page.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(
        name = "CRUD APIs for Category resource"
)
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Add Category Rest API",
            description = "Add Category Rest API is used to create new category into database",
            method = "POST"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status 201 Created"
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO dto){
        CategoryDTO response = categoryService.addCategory(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Category by Id Rest API",
            description = "Get Category by Id Rest API is used to fetch Category by id from database",
            method = "GET"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable long id){
        CategoryDTO categoryDTO = categoryService.getCategory(id);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all Categories Rest API",
            description = "Get all Categories Rest API is used to fetch all Categories from database",
            method = "GET"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Category Rest API",
            description = "Update Category Rest API is used to update category in database",
            method = "PUT"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    @PutMapping("/{category_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO dto, @PathVariable long category_id){
        CategoryDTO response = categoryService.updateCategory(dto, category_id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Category Rest API",
            description = "Delete Category Rest API is used to delete category from database",
            method = "DELETE"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    @DeleteMapping("/{category_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable long category_id){
        categoryService.deleteCategory(category_id);
        return new ResponseEntity<>("Category deleted successfully!", HttpStatus.OK);
    }
}