package com.kart.catalog.category.dto;

import java.time.OffsetDateTime;

public record MethodArgumentTypeMismatchExceptionResponse(
        String message,
        OffsetDateTime timestamp
) {
}
