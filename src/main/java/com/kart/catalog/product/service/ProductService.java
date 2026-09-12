package com.kart.catalog.product.service;

import com.kart.catalog.category.entity.CategoryEntity;
import com.kart.catalog.category.exception.CategoryNotFoundException;
import com.kart.catalog.category.repository.CategoryRepository;
import com.kart.catalog.product.dto.ProductCreateRequest;
import com.kart.catalog.product.dto.ProductResponse;
import com.kart.catalog.product.entity.ProductEntity;
import com.kart.catalog.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse createProduct(ProductCreateRequest request) {
        CategoryEntity categoryEntity = categoryRepository
                .findById(request.categoryId())
                .orElseThrow(()->new CategoryNotFoundException(request.categoryId()));
        ProductEntity productEntity = new ProductEntity(
                categoryEntity,
                request.name(),
                request.sku(),
                request.description(),
                request.price(),
                request.status()
        );
        ProductEntity savedProductEntity = productRepository.save(productEntity);
        return ProductResponse.getProductResponse(savedProductEntity);
    }
}
