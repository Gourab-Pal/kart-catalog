package com.kart.catalog.product.controller;

import com.kart.catalog.product.dto.ProductCreateRequest;
import com.kart.catalog.product.dto.ProductResponse;
import com.kart.catalog.product.service.ProductService;
import jakarta.validation.Valid;
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
}
