package com.kart.catalog.product.dto;

import com.kart.catalog.category.dto.CategoryResponse;
import com.kart.catalog.product.entity.ProductEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        CategoryResponse category,
        String name,
        String sku,
        String description,
        BigDecimal price,
        String status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public static ProductResponse getProductResponse(ProductEntity entity) {
        return new ProductResponse(
                entity.getId(),
                CategoryResponse.getCategoryResponse(entity.getCategory()),
                entity.getName(),
                entity.getSku(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
