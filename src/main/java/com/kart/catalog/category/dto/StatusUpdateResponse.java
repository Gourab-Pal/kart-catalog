package com.kart.catalog.category.dto;

import com.kart.catalog.category.entity.CategoryEntity;

import java.util.UUID;

public record StatusUpdateResponse(
        UUID id,
        String status,
        String message
) {
    public static StatusUpdateResponse getStatusUpdateResponse(CategoryEntity entity) {
        return new StatusUpdateResponse(
                entity.getId(),
                entity.getStatus(),
                "Status set as: " + entity.getStatus()
        );
    }
}
