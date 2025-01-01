package com.craftly.craftlycore.exception.auth;

public class CookieDeserializationException extends RuntimeException {
    public CookieDeserializationException(Throwable cause) {
        super("Failed to deserialize object from cookie.", cause);
    }
}