package com.kart.catalog.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CategoryCreateRequest(

        @NotBlank(message = "Category name is required")
        @Size(max = 100, message = "Category name can not be more than 100 characters long")
        String name,

        @NotBlank(message = "Category code is required")
        @Size(max = 10, message = "Category code can not be more than 10 characters long")
        @Pattern(
                regexp = "[A-Z0-9_]+",
                message = "Category code can only contains uppercase characters, numbers and underscore(_)"
        )
        String code,

        @NotBlank(message = "Category status is required")
        @Pattern(
                regexp = "ENABLED|DISABLED",
                message = "Category status can be either ENABLED or DISABLED"
        )
        String status
) {
}
