package com.kart.catalog.category.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CategoryDeleteResponse(
        UUID id,
        String message,
        OffsetDateTime timestamp
) {
    public static CategoryDeleteResponse getCategoryDeleteResponse(UUID id) {
        return new CategoryDeleteResponse(
                id,
                "Category id deleted",
                OffsetDateTime.now()
        );
    }
}
