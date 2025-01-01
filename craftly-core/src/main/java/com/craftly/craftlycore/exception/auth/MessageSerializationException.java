package com.craftly.craftlycore.exception.auth;

public class MessageSerializationException extends RuntimeException {
    public MessageSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}