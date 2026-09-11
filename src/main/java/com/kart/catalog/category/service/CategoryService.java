package com.kart.catalog.category.service;

import com.kart.catalog.category.dto.CategoryCreateRequest;
import com.kart.catalog.category.dto.CategoryDeleteResponse;
import com.kart.catalog.category.dto.CategoryResponse;
import com.kart.catalog.category.entity.CategoryEntity;
import com.kart.catalog.category.exception.CategoryNotFoundException;
import com.kart.catalog.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Cacheable(
            cacheNames = "categories",
            key = "#status == null ? 'all' : #status"
    )
    public List<CategoryResponse> getAllCategories(
            String status
    ) {
        List<CategoryEntity> categoryEntities = categoryRepository.findWithFilter(status);
        List<CategoryResponse> responses = new ArrayList<>();
        for(CategoryEntity categoryEntity: categoryEntities) {
            responses.add(CategoryResponse.getCategoryResponse(categoryEntity));
        }
        return responses;
    }

    public CategoryResponse getCategoryById(UUID id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(()-> new CategoryNotFoundException(id));
        return CategoryResponse.getCategoryResponse(categoryEntity);
    }

    @CacheEvict(
            cacheNames = "categories",
            allEntries = true
    )
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        CategoryEntity entity = new CategoryEntity(request.name(), request.code(), request.status());
        return CategoryResponse.getCategoryResponse(categoryRepository.save(entity));
    }

    @Transactional
    public CategoryDeleteResponse deleteCategory(UUID id) {
        categoryRepository.findById(id).orElseThrow(()->new CategoryNotFoundException(id));
        categoryRepository.deleteById(id);
        return CategoryDeleteResponse.getCategoryDeleteResponse(id);
    }
}
