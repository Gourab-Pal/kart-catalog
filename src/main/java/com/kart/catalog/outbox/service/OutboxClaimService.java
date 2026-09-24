package com.kart.catalog.outbox.service;

import com.kart.catalog.outbox.entity.OutboxEventEntity;
import com.kart.catalog.outbox.repository.OutboxEventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OutboxClaimService {

    private static final int BATCH_SIZE = 10;
    private static final long LOCK_DURATION_SECONDS = 60;
    private static final int MAX_ATTEMPTS = 5;

    private final OutboxEventRepository outboxEventRepository;

    public OutboxClaimService(OutboxEventRepository outboxEventRepository) {
        this.outboxEventRepository = outboxEventRepository;
    }

    @Transactional
    public List<OutboxEventEntity> claimDueEvents() {
        OffsetDateTime now = OffsetDateTime.now();
        List<OutboxEventEntity> events = outboxEventRepository.findEventsToProcess(now, BATCH_SIZE);
        OffsetDateTime lockedUntil = now.plusSeconds(LOCK_DURATION_SECONDS);

        for (OutboxEventEntity event : events) {
            event.markProcessing(lockedUntil);
        }

        return events;
    }

    @Transactional
    public void markPublished(UUID eventId) {
        OutboxEventEntity event =  outboxEventRepository.findById(eventId).orElseThrow();
        event.markPublished();
    }

    @Transactional
    public void recordPublishFailure(UUID eventId, String error) {
        OutboxEventEntity event =  outboxEventRepository.findById(eventId).orElseThrow();
        if(event.getAttemptCount() >= MAX_ATTEMPTS) {
            event.markFailed(error);
            return;
        }

        int shift = Math.min(event.getAttemptCount()-1, 6);
        long retryDelaySeconds = Math.min(60, 1L << shift);
        OffsetDateTime nextAttemptAt = OffsetDateTime.now().plusSeconds(retryDelaySeconds);
        event.scheduleRetry(nextAttemptAt, error);
    }
}
