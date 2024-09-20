package com.spike.user.exceptions;

public class DtoToEntityConversionException extends RuntimeException{
    public DtoToEntityConversionException(String message) {
        super(message);
    }
    public DtoToEntityConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
