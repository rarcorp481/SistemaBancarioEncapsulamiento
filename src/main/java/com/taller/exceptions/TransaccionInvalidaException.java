package com.taller.exceptions;

public class TransaccionInvalidaException extends RuntimeException {
    public TransaccionInvalidaException(String message) {
        super(message);
    }
}
