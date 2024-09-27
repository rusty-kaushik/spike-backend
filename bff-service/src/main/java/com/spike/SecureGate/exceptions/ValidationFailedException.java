package com.spike.SecureGate.exceptions;

import lombok.Getter;

@Getter
public class ValidationFailedException extends RuntimeException{

    private final String error;

    public ValidationFailedException(String error, String message) {
        super(message);
        this.error = error;
    }
}
