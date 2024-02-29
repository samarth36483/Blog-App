package com.springboot.blog.page.service;

import com.springboot.blog.page.dto.CategoryDTO;
import com.springboot.blog.page.exception.ResourceNotFoundException;
import com.springboot.blog.page.model.Category;
import com.springboot.blog.page.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO dto) {
        Category category = mapper.map(dto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        CategoryDTO response = mapper.map(savedCategory, CategoryDTO.class);
        return response;
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        return null;
    }

    @Override
    public CategoryDTO getCategory(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        CategoryDTO categoryDTO = mapper.map(category, CategoryDTO.class);
        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> mapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO dto, long category_id) {
        Category category = categoryRepository.findById(category_id).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id"));
        if(dto.getName() != null)
            category.setName(dto.getName());
        if(dto.getDescription() != null)
            category.setDescription(dto.getDescription());

        Category savedCategory = categoryRepository.save(category);
        CategoryDTO categoryDTO = mapper.map(category, CategoryDTO.class);
        return categoryDTO;
    }

    @Override
    public void deleteCategory(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id"));
        categoryRepository.delete(category);
    }
}
