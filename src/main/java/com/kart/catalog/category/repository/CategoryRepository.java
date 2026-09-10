package com.kart.catalog.category.repository;

import com.kart.catalog.category.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    @Query("""
        SELECT category
        FROM CategoryEntity category
        WHERE :status IS NULL OR category.status = :status
    """)
    CategoryEntity findWithFilter(@Param("status") String status);
}
