package com.craftly.craftlycore.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException() {
        super("Authorization header is missing.");
    }
}