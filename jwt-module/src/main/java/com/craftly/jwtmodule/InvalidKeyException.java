package com.craftly.jwtmodule;

public class InvalidKeyException extends RuntimeException {

    public InvalidKeyException(String message, Throwable cause) {
        super(message, cause);
    }

}