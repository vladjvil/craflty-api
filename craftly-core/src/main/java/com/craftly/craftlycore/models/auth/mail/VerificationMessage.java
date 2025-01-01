package com.craftly.craftlycore.models.auth.mail;

public record VerificationMessage(
        String username,
        String to,
        String token
) { }