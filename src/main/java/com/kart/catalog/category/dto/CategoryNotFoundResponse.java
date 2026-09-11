package com.kart.catalog.category.dto;

import com.kart.catalog.category.exception.CategoryNotFoundException;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CategoryNotFoundResponse(
        UUID categoryId,
        String message,
        OffsetDateTime timestamp
) {
    public static CategoryNotFoundResponse getCategoryNotFoundResponse(CategoryNotFoundException exception) {
        return new CategoryNotFoundResponse(
                exception.getCategoryId(),
                exception.getMessage(),
                OffsetDateTime.now()
        );
    }
}
