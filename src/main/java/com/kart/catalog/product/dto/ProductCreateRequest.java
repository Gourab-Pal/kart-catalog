package com.kart.catalog.product.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductCreateRequest(

        @NotNull(message = "Category id is required")
        UUID categoryId,

        @NotBlank(message = "Product name is required")
        @Size(max = 150, message = "Product name can not exceed 150 character")
        String name,

        @NotBlank(message = "SKU is required")
        @Size(max = 50, message = "SKU can not exceed 50 characters")
        @Pattern(
                regexp = "[A-Z0-9_]+",
                message = "SKU can only contain uppercase characters, numbers and underscore"
        )
        String sku,

        @Size(max = 2000, message = "Description can not exceed 2000 characters")
        String description,

        @NotNull(message = "Product price is required")
        @DecimalMin(value = "0.00", message = "Price can not be negative")
        @Digits(
                integer = 10,
                fraction = 2,
                message = "Price can have up to 10 integer digits and 2 decimal places"
        )
        BigDecimal price,

        @NotBlank(message = "Product status is required")
        @Pattern(
                regexp = "ENABLED|DISABLED",
                message = "Status must be either ENABLED or DISABLED"
        )
        String status
) {
}
