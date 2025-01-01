package com.craftly.craftlycore.models.saga;

public record SagaEventDTO(
    String id,
    String payload,
    String eventType
) { }