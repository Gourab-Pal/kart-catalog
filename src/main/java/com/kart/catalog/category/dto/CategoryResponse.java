package com.kart.catalog.category.dto;

import com.kart.catalog.category.entity.CategoryEntity;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        String code,
        String status
) {
    public static CategoryResponse getCategoryResponse(CategoryEntity categoryEntity) {
        return new CategoryResponse(
                categoryEntity.getId(),
                categoryEntity.getName(),
                categoryEntity.getCode(),
                categoryEntity.getStatus()
        );
    }
}
