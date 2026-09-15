package com.kart.catalog.product.controller;

import com.kart.catalog.product.dto.*;
import com.kart.catalog.product.service.ProductService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

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
            String sortDirection,
            @RequestParam(required = false)
            BigDecimal minPrice,
            @RequestParam(required = false)
            BigDecimal maxPrice,
            @RequestParam(required = false)
            String searchProductName,
            @RequestParam(required = false)
            UUID categoryId
    ) {
        return productService.getAllProducts(
                status,
                minPrice,
                maxPrice,
                searchProductName,
                categoryId,
                page,
                size,
                sortBy,
                sortDirection
        );
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductById(
           @PathVariable
           UUID productId
    ) {
        return productService.getProductById(productId);
    }

    @PatchMapping("/{productId}/updateDescription")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProductDescription(
            @PathVariable
            UUID productId,
            @Valid
            @RequestBody
            ProductDescriptionUpdateRequest request
    ) {
        return productService.updateProductDescription(productId, request);
    }

    @PatchMapping("/{productId}/updatePrice")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProductPrice(
            @PathVariable
            UUID productId,
            @Valid
            @RequestBody
            ProductPriceUpdateRequest request
    ) {
        return productService.updatePrice(productId, request);
    }

    @PatchMapping("/{productId}/enable")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse enableProduct(
            @PathVariable
            UUID productId
    ) {
        return productService.enableProduct(productId);
    }

    @PatchMapping("/{productId}/disable")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse disableProduct(
            @PathVariable
            UUID productId
    ) {
        return productService.disableProduct(productId);
    }

    @DeleteMapping("/{productId}/delete")
    @ResponseStatus(HttpStatus.OK)
    public ProductDeleteResponse delete(
            @PathVariable
            UUID productId
    ) {
        return productService.deleteProduct(productId);
    }
}
