package com.kart.catalog.category.service;

import com.kart.catalog.category.dto.CategoryResponse;
import com.kart.catalog.category.entity.CategoryEntity;
import com.kart.catalog.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponse getAllCategories(
            String status
    ) {
        CategoryEntity categoryEntity = categoryRepository.findWithFilter(status);
        return CategoryResponse.getCategoryResponse(categoryEntity);
    }

    public CategoryResponse getCategoryById(UUID id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow();
        return CategoryResponse.getCategoryResponse(categoryEntity);
    }
}
