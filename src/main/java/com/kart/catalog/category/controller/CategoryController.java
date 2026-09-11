package com.kart.catalog.category.controller;

import com.kart.catalog.category.dto.CategoryCreateRequest;
import com.kart.catalog.category.dto.CategoryResponse;
import com.kart.catalog.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> getCategories(
            @RequestParam(required = false)
            String status
    ) {
        return categoryService.getAllCategories(status);
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponse getCategoryById(
            @PathVariable
            UUID categoryId
    ){
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@Valid @RequestBody CategoryCreateRequest request) {
        return categoryService.createCategory(request);
    }
}
