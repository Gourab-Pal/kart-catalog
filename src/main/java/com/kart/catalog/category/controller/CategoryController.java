package com.kart.catalog.category.controller;

import com.kart.catalog.category.dto.CategoryResponse;
import com.kart.catalog.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponse getCategories(
            @RequestParam(required = false)
            String status
    ) {
        return categoryService.getAllCategories(status);
    }
}
