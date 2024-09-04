package com.spike.user.exceptions;

public class UnexpectedException extends RuntimeException{
    public UnexpectedException(String message) {
        super(message);
    }
    public UnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }
}
