package com.kart.catalog.category.service;

import com.kart.catalog.category.dto.*;
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

    @CacheEvict(
            cacheNames = "categories",
            allEntries = true
    )
    @Transactional
    public CategoryDeleteResponse deleteCategory(UUID id) {
        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(()->new CategoryNotFoundException(id));
        categoryRepository.delete(entity);
        return CategoryDeleteResponse.getCategoryDeleteResponse(id);
    }

    @CacheEvict(
            cacheNames = "categories",
            allEntries = true
    )
    @Transactional
    public CategoryNameUpdateResponse updateName(UUID id, CategoryNameUpdateRequest request) {
        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(()->new CategoryNotFoundException(id));
        String previousName = entity.getName();
        entity.updateName(request.name());
        CategoryEntity savedEntity = categoryRepository.save(entity);
        return CategoryNameUpdateResponse.getNameUpdateResponse(savedEntity, previousName);
    }

    @CacheEvict(
            cacheNames = "categories",
            allEntries = true
    )
    @Transactional
    public StatusUpdateResponse enable(UUID id) {
        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(()->new CategoryNotFoundException(id));
        entity.enable();
        CategoryEntity savedEntity = categoryRepository.save(entity);
        return StatusUpdateResponse.getStatusUpdateResponse(savedEntity);
    }

    @CacheEvict(
            cacheNames = "categories",
            allEntries = true
    )
    @Transactional
    public StatusUpdateResponse disable(UUID id) {
        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(()->new CategoryNotFoundException(id));
        entity.disable();
        CategoryEntity savedEntity = categoryRepository.save(entity);
        return StatusUpdateResponse.getStatusUpdateResponse(savedEntity);
    }
}
