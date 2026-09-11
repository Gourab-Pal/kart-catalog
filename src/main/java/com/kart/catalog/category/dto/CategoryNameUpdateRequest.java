package com.kart.catalog.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryNameUpdateRequest(

        @NotBlank(message = "Update category name is required")
        @Size(max = 100, message = "Updated category name can not be more than 100 characters long")
        String name
) {
}
