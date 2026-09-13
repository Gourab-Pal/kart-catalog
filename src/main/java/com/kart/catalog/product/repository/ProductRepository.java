package com.kart.catalog.product.repository;

import com.kart.catalog.product.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Query("""
        SELECT product
        FROM ProductEntity product
        WHERE (:status IS NULL OR product.status = :status)
            AND (:minPrice IS NULL OR product.price >= :minPrice)
            AND (:maxPrice IS NULL OR product.price <= :maxPrice)
            AND (:searchProductName = '' OR LOWER(product.name) LIKE CONCAT('%', :searchProductName, '%'))
    """)
    Page<ProductEntity> findWithFilters(
            @Param("status") String status,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("searchProductName") String searchProductName,
            Pageable pageable
    );
}
