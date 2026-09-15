package com.kart.catalog.product.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductDeleteResponse(
        UUID id,
        String message,
        OffsetDateTime timestamp
) {
    public static ProductDeleteResponse getDeleteResponse(UUID id) {
        return new ProductDeleteResponse(
                id,
                "Product deleted",
                OffsetDateTime.now()
        );
    }
}
