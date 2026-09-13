package com.kart.catalog.product.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException{

    private final UUID productId;

    public ProductNotFoundException(UUID productId) {
        super("Product Id " + productId + " does not exist in database");
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
