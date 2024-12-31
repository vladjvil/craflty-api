package com.craftly.craftlycore.saga;

public record SagaEventDTO(
    String id,
    String payload,
    String eventType
) { }