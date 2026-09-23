package com.kart.catalog.kafka.event;

import java.util.UUID;

public record ProductCreatedPayload(
        UUID productId
) {
}
