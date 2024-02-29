package com.springboot.blog.page.service;

import com.springboot.blog.page.dto.CategoryDTO;
import com.springboot.blog.page.model.Category;

import java.util.List;

public interface CategoryService {

    CategoryDTO addCategory(CategoryDTO dto);
    CategoryDTO getCategoryByName(String name);
    CategoryDTO getCategory(long id);
    List<CategoryDTO> getAllCategories();
    CategoryDTO updateCategory(CategoryDTO dto, long category_id);
    void deleteCategory(long id);
}
