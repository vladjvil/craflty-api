package com.craftly.craftlycore.models.outbox;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OutboxStatus {
    PENDING("В ожидании"),
    PROCESSED("Обработалась");

    private final String status;
}