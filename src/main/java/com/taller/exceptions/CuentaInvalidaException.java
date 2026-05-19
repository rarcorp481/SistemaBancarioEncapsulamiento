package com.taller.exceptions;

public class CuentaInvalidaException extends RuntimeException {
    public CuentaInvalidaException(String message) {
        super(message);
    }
}
