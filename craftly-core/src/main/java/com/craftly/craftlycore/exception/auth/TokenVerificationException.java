package com.craftly.craftlycore.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenVerificationException extends RuntimeException {
    public TokenVerificationException() {
        super("Authentication failure: Token missing, invalid, revoked or expired.");
    }
}