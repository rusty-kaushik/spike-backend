package com.spike.SecureGate.exceptions;

import lombok.Getter;

@Getter
public class BlogNotFoundException extends RuntimeException{

    private final String error;

    public BlogNotFoundException(String error, String message) {
        super(message);
        this.error = error;
    }
}
