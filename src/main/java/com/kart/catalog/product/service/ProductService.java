package com.kart.catalog.product.service;

import com.kart.catalog.category.entity.CategoryEntity;
import com.kart.catalog.category.exception.CategoryNotFoundException;
import com.kart.catalog.category.repository.CategoryRepository;
import com.kart.catalog.common.validation.GenericValidators;
import com.kart.catalog.kafka.CatalogEventPublisher;
import com.kart.catalog.outbox.service.OutboxEventService;
import com.kart.catalog.product.dto.*;
import com.kart.catalog.product.exception.ProductNotFoundException;
import com.kart.catalog.product.entity.ProductEntity;
import com.kart.catalog.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CatalogEventPublisher  catalogEventPublisher;
    private final OutboxEventService  outboxEventService;

    public ProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            CatalogEventPublisher  catalogEventPublisher,
            OutboxEventService  outboxEventService
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.catalogEventPublisher = catalogEventPublisher;
        this.outboxEventService = outboxEventService;
    }

    @CacheEvict(
            cacheNames = "products",
            allEntries = true
    )
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        CategoryEntity categoryEntity = categoryRepository
                .findById(request.categoryId())
                .orElseThrow(()->new CategoryNotFoundException(request.categoryId()));
        if("DISABLED".equals(categoryEntity.getStatus())) {
            throw new IllegalArgumentException("Can not create product with disabled category");
        }
        ProductEntity productEntity = new ProductEntity(
                categoryEntity,
                request.name(),
                request.sku(),
                request.description(),
                request.price(),
                request.status()
        );
        ProductEntity savedProductEntity = productRepository.save(productEntity);
        outboxEventService.saveProductCreatedEvent(savedProductEntity.getId());
        catalogEventPublisher.publishProductCreatedEvent(savedProductEntity.getId());
        return ProductResponse.getProductResponse(savedProductEntity);
    }

    @Cacheable(
            cacheNames = "products",
            key = "(#status ?: 'ALL') + ':' + " +
                    "(#minPrice ?: 'NONE') + ':' + " +
                    "(#maxPrice ?: 'NONE') + ':' + " +
                    "(#searchProductName ?: 'NONE') + ':' + " +
                    "(#categoryId ?: 'ALL') + ':' + " +
                    "#page + ':' + " +
                    "#size + ':' + " +
                    "#sortBy + ':' + " +
                    "#sortDirection"
    )
    @Transactional
    public ProductPageResponse getAllProducts(
            String status,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String searchProductName,
            UUID categoryId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        searchProductName = GenericValidators.normalizeProductNameSearchString(searchProductName);
        GenericValidators.validateProductFilters(status, minPrice, maxPrice, page, size, sortBy, sortDirection);
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProductEntity> productPages = productRepository.findWithFilters(
                status,
                minPrice,
                maxPrice,
                searchProductName,
                categoryId,
                pageable
        );
        Page<ProductResponse> pageResponse = productPages.map(ProductResponse::getProductResponse);
        return ProductPageResponse.from(pageResponse);
    }

    @Transactional
    public ProductResponse getProductById(UUID productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));
        return ProductResponse.getProductResponse(productEntity);
    }

    @CacheEvict(
            cacheNames = "products",
            allEntries = true
    )
    @Transactional
    public ProductResponse updateProductDescription(UUID productId, ProductDescriptionUpdateRequest request) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));
        productEntity.updateDescription(request.description());
        ProductEntity savedEntity = productRepository.save(productEntity);
        return ProductResponse.getProductResponse(savedEntity);
    }

    @CacheEvict(
            cacheNames = "products",
            allEntries = true
    )
    @Transactional
    public ProductResponse updatePrice(UUID productId, ProductPriceUpdateRequest request) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));
        productEntity.updatePrice(request.price());
        ProductEntity savedEntity = productRepository.save(productEntity);
        return ProductResponse.getProductResponse(savedEntity);
    }

    @CacheEvict(
            cacheNames = "products",
            allEntries = true
    )
    @Transactional
    public ProductResponse enableProduct(UUID productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));
        productEntity.enable();
        ProductEntity savedEntity = productRepository.save(productEntity);
        return ProductResponse.getProductResponse(savedEntity);
    }

    @CacheEvict(
            cacheNames = "products",
            allEntries = true
    )
    @Transactional
    public ProductResponse disableProduct(UUID productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));
        productEntity.disable();
        ProductEntity savedEntity = productRepository.save(productEntity);
        return ProductResponse.getProductResponse(savedEntity);
    }

    @CacheEvict(
            cacheNames = "products",
            allEntries = true
    )
    @Transactional
    public ProductDeleteResponse deleteProduct(UUID productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));
        productRepository.delete(productEntity);
        return ProductDeleteResponse.getDeleteResponse(productEntity.getId());
    }
}
