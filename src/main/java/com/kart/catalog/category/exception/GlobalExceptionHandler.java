package com.kart.catalog.category.exception;

import com.kart.catalog.category.dto.CategoryNotFoundResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CategoryNotFoundResponse handleCategoryNotFoundException(CategoryNotFoundException exception) {
        return CategoryNotFoundResponse.getCategoryNotFoundResponse(exception);
    }
}
