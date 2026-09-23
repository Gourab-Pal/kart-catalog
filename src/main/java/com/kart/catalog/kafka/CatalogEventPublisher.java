package com.kart.catalog.kafka;

import com.kart.catalog.kafka.event.CatalogEvent;
import com.kart.catalog.kafka.event.ProductCreatedPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class CatalogEventPublisher {

    private final KafkaTemplate<String, CatalogEvent> kafkaTemplate;
    private final String catalogEventTopic;

    public CatalogEventPublisher(
            KafkaTemplate<String, CatalogEvent> kafkaTemplate,
            @Value("${kafka.topic.catalog-events}") String catalogEventTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.catalogEventTopic = catalogEventTopic;
    }

    public void publishProductCreatedEvent(UUID productId) {
        ProductCreatedPayload productCreatedPayload = new ProductCreatedPayload(productId);

        CatalogEvent event = new CatalogEvent(
                UUID.randomUUID(),
                "PRODUCT_CREATED",
                1,
                OffsetDateTime.now(),
                productCreatedPayload
        );

        Message<CatalogEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, catalogEventTopic)
                .setHeader(KafkaHeaders.KEY, productId.toString())
                .setHeader("source-service", "kart-catalog".getBytes(StandardCharsets.UTF_8))
                .build();

        CompletableFuture<SendResult<String, CatalogEvent>> future = kafkaTemplate.send(message);
    }
}
