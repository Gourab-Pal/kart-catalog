package com.kart.catalog.outbox.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kart.catalog.kafka.event.ProductCreatedPayload;
import com.kart.catalog.outbox.entity.OutboxEventEntity;
import com.kart.catalog.outbox.repository.OutboxEventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OutboxEventService {

    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    public OutboxEventService(OutboxEventRepository outboxEventRepository, ObjectMapper objectMapper) {
        this.outboxEventRepository = outboxEventRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void saveProductCreatedEvent(UUID productId) {
        ProductCreatedPayload productCreatedPayload = new ProductCreatedPayload(productId);
        OutboxEventEntity outboxEvent =   new OutboxEventEntity(
                "product",
                productId,
                "PRODUCT_CREATED",
                1,
                objectMapper.valueToTree(productCreatedPayload)
        );
        outboxEventRepository.save(outboxEvent);
    }
}
