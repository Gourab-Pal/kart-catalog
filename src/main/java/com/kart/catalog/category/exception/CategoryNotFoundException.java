package com.kart.catalog.category.exception;

import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(UUID id) {
        super("Category id: " + id + " does not exist in database.");
    }
}
