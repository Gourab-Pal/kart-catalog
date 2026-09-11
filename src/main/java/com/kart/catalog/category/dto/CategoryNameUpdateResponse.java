package com.kart.catalog.category.dto;

import com.kart.catalog.category.entity.CategoryEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CategoryNameUpdateResponse(
        String previousName,
        String updatedName,
        UUID id,
        OffsetDateTime timestamp
) {
    public static CategoryNameUpdateResponse getNameUpdateResponse(CategoryEntity entity, String previousName) {
        return new CategoryNameUpdateResponse(
                previousName,
                entity.getName(),
                entity.getId(),
                entity.getUpdatedAt()
        );
    }
}
