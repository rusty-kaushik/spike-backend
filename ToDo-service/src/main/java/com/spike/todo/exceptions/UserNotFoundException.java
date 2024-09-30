package com.spike.todo.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final String error;

    public UserNotFoundException(String error, String message) {
        super(message);
        this.error = error;
    }
}