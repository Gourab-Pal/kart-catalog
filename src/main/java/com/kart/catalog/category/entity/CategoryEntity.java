package com.kart.catalog.category.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "categories", schema = "kart_catalog")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false, updatable = true)
    private OffsetDateTime updatedAt;

    protected CategoryEntity() {}

    public CategoryEntity(
            String name,
            String code
    ) {
        this.name = name;
        this.code = code;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void updateTimestamp() {
        this.updatedAt = OffsetDateTime.now();
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public String getStatus() {
        return this.status;
    }
}
