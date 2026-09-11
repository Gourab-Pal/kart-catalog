package com.kart.catalog.category.dto;

import com.kart.catalog.category.exception.CategoryNotFoundException;
import java.time.OffsetDateTime;

public record CategoryNotFoundResponse(
        String message,
        OffsetDateTime timestamp
) {
    public static CategoryNotFoundResponse getCategoryNotFoundResponse(CategoryNotFoundException exception) {
        return new CategoryNotFoundResponse(
                exception.getMessage(),
                OffsetDateTime.now()
        );
    }
}
