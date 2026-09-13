package com.kart.catalog.product.dto;

import com.kart.catalog.product.exception.ProductNotFoundException;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductNotFoundExceptionResponse(
        UUID productId,
        String message,
        OffsetDateTime timestamp
) {
    public static ProductNotFoundExceptionResponse getProductNotFoundResponse(ProductNotFoundException exception) {
        return new ProductNotFoundExceptionResponse(
                exception.getProductId(),
                exception.getMessage(),
                OffsetDateTime.now()
        );
    }
}
