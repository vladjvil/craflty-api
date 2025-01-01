package com.craftly.craftlycore.models.auth.mail;

public record UserPasswordResetMessage(
        String email,
        String newEncodedPassword
) { }