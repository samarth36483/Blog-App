package com.springboot.blog.page.repository;

import com.springboot.blog.page.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
