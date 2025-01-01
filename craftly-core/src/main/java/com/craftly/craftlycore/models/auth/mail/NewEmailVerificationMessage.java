package com.craftly.craftlycore.models.auth.mail;

public record NewEmailVerificationMessage (
        String to,
        int verificationCode
) { }