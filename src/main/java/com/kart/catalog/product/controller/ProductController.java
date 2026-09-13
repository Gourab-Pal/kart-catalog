package com.kart.catalog.product.controller;

import com.kart.catalog.product.dto.ProductCreateRequest;
import com.kart.catalog.product.dto.ProductResponse;
import com.kart.catalog.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(
            @Valid @RequestBody ProductCreateRequest request
            ) {
        return productService.createProduct(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponse> getProducts(
            @RequestParam(required = false)
            String status,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "3")
            int size,
            @RequestParam(defaultValue = "createdAt")
            String sortBy,
            @RequestParam(defaultValue = "desc")
            String sortDirection
    ) {
        return productService.getAllProducts(status, page, size, sortBy, sortDirection);
    }

}
