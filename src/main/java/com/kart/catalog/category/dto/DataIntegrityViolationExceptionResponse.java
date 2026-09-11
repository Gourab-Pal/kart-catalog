package com.kart.catalog.category.dto;

import java.time.OffsetDateTime;

public record DataIntegrityViolationExceptionResponse(
        String message,
        OffsetDateTime timestamp
) {
}
