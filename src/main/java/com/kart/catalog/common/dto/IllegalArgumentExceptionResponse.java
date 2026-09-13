package com.kart.catalog.common.dto;

import java.time.OffsetDateTime;

public record IllegalArgumentExceptionResponse(
        String message,
        OffsetDateTime timestamp
) {
}
