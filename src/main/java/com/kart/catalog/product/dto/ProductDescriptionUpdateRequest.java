package com.kart.catalog.product.dto;

import jakarta.validation.constraints.Size;

public record ProductDescriptionUpdateRequest(
        @Size(max = 2000, message = "Description can not exceed 2000 characters")
        String description
) {
}
