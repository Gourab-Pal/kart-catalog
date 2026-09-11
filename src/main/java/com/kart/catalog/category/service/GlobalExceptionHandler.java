package com.kart.catalog.category.service;

import com.kart.catalog.category.dto.CategoryNotFoundResponse;
import com.kart.catalog.category.exception.CategoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CategoryNotFoundResponse handleCategoryNotFoundException(CategoryNotFoundException exception) {
        return CategoryNotFoundResponse.getCategoryNotFoundResponse(exception);
    }
}
