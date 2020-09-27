package com.example.exceptions;

public class NegativesNotAllowedException extends RuntimeException {
    public NegativesNotAllowedException(String message) {
        super(message);
    }
}
