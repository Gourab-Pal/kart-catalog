package com.kart.catalog.product.entity;

import com.kart.catalog.category.entity.CategoryEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "products", schema = "kart_catalog")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "sku", nullable = false, unique = true, length = 50)
    private String sku;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    protected ProductEntity(){}

    public ProductEntity(
            CategoryEntity category,
            String name,
            String sku,
            String description,
            BigDecimal price,
            String status
    ) {
        this.category = category;
        this.name = name;
        this.sku = sku;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void updateTimestamp() {
        this.updatedAt = OffsetDateTime.now();
    }

    public UUID getId() {return id;}
    public CategoryEntity getCategory() {return category;}
    public String getName() {return name;}
    public String getSku() {return sku;}
    public String getDescription() {return description;}
    public BigDecimal getPrice() {return price;}
    public String getStatus() {return status;}
    public OffsetDateTime getCreatedAt() {return createdAt;}
    public OffsetDateTime getUpdatedAt() {return updatedAt;}
}
