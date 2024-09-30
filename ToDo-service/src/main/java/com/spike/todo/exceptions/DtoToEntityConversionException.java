package com.spike.todo.exceptions;

import lombok.Getter;

@Getter
public class DtoToEntityConversionException extends RuntimeException{

    private final String error;

    public DtoToEntityConversionException(String error, String message) {
        super(message);
        this.error = error;
    }
}
