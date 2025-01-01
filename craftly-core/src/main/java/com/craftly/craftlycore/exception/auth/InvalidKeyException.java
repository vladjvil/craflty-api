package com.craftly.craftlycore.exception.auth;

public class InvalidKeyException extends RuntimeException {
    public InvalidKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}