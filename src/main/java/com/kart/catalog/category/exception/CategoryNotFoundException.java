package com.kart.catalog.category.exception;

import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException{
    private final UUID categoryId;
    public CategoryNotFoundException(UUID id) {
        super("Category does not exist in database.");
        this.categoryId = id;
    }

    public UUID getCategoryId() {
        return categoryId;
    }
}
