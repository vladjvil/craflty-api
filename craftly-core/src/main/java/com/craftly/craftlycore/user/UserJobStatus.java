package com.craftly.craftlycore.user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserJobStatus {
    FREELANCER("Фрилансенр - работник"),
    EMPLOYER("Наниматель");

    private final String jobStatus;
}