package com.spike.todo.exceptions;

import lombok.Getter;

@Getter
public class DepartmentNotFoundException extends RuntimeException{

    private final String error;

    public DepartmentNotFoundException(String error, String message) {
        super(message);
        this.error = error;
    }
}
