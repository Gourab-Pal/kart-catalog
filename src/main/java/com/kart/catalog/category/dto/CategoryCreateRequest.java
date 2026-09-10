package com.kart.catalog.category.dto;

public record CategoryCreateRequest(
        String name,
        String code,
        String status
) {
}
