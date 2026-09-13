package com.kart.catalog.product.repository;

import com.kart.catalog.product.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Query("""
        SELECT product
        FROM ProductEntity product
        WHERE :status IS NULL OR product.status = :status
    """)
    Page<ProductEntity> findWithFilters(
            @Param("status") String status,
            Pageable pageable
    );
}
