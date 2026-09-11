package com.kart.catalog.category.exception;

public class CategoryNameAlreadyExistsException extends RuntimeException{
    private final String name;
    public CategoryNameAlreadyExistsException(String name) {
        super("Updated category name can not be same as current name");
        this.name = name;
    }

    public String getUpdatedName() {
        return name;
    }
}
