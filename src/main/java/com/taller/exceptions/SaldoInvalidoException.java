package com.taller.exceptions;

public class SaldoInvalidoException extends RuntimeException {
    public SaldoInvalidoException(String message) {
        super(message);
    }
}
