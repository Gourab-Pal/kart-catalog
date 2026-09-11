package com.kart.catalog.category.dto;

import java.time.OffsetDateTime;
import java.util.Map;

public record ValidationErrorResponse(
        String message,
        Map<String,String> errors,
        OffsetDateTime timestamp
) {
}
