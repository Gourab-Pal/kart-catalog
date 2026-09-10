package com.kart.catalog.health.dto;

import java.time.OffsetDateTime;

public record HealthResponse(
        String service,
        String status,
        OffsetDateTime timestamp
) {
    public static HealthResponse getHealthResponse() {
        return new HealthResponse(
                "kart-catalog",
                "up",
                OffsetDateTime.now()
        );
    }
}
