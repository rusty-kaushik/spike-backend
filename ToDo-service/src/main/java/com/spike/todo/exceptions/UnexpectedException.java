package com.spike.todo.exceptions;

import lombok.Getter;

@Getter
public class UnexpectedException extends RuntimeException{

    private final String error;

    public UnexpectedException(String error, String message) {
        super(message);
        this.error = error;
    }
}
