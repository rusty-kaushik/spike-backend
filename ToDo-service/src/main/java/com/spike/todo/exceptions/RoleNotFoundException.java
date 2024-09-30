package com.spike.todo.exceptions;

import lombok.Getter;

@Getter
public class RoleNotFoundException extends RuntimeException{

    private final String error;

    public RoleNotFoundException(String error, String message) {
        super(message);
        this.error = error;
    }
}
