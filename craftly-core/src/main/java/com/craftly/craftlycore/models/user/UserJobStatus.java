package com.craftly.craftlycore.models.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserJobStatus {
    FREELANCER("Фрилансенр - работник"),
    EMPLOYER("Наниматель");

    private final String jobStatus;
}