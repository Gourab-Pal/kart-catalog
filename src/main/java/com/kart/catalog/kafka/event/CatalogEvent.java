package com.kart.catalog.kafka.event;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CatalogEvent(
        UUID eventId,
        String eventType,
        int eventVersion,
        OffsetDateTime occurredAt,
        ProductCreatedPayload productCreatedPayload
) {
}
