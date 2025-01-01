package com.craftly.craftlycore.models.auth.mail;

public record ForgotPasswordMessage(
        String email,
        String token
) { }