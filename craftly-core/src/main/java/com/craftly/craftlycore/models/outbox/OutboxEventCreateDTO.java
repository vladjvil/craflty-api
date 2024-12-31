package com.craftly.craftlycore.models.outbox;

public record OutboxEventCreateDTO(
        String eventType,
        String payload,
        OutboxStatus status
) { }
