package com.spike.user.exceptions;

public class ValidationFailedException extends RuntimeException {

    private final String error;


    public ValidationFailedException(String error, String message) {
        super(message);
        this.error = error;
    }
}

