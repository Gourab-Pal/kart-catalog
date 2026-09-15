package com.kart.catalog.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductPriceUpdateRequest(
        @NotNull(message = "Product price is required")
        @DecimalMin(value = "0.00", message = "Price can not be negative")
        @Digits(
                integer = 10,
                fraction = 2,
                message = "Price can have up to 10 integer digits and 2 decimal places"
        )
        BigDecimal price
) {
}
