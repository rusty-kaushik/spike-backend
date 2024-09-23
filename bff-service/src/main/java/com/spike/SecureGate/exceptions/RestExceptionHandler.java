package com.spike.SecureGate.exceptions;

import com.spike.SecureGate.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {UnexpectedException.class})
    public ResponseEntity<Object> handleUnexpectedException(UnexpectedException ex) {
        return ResponseHandler.errorResponseBuilder(
                ex.getError(),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {ValidationFailedException.class})
    public ResponseEntity<Object> handleValidationFailedException(ValidationFailedException ex) {
        return ResponseHandler.errorResponseBuilder(
                ex.getError(),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {BlogNotFoundException.class})
    public ResponseEntity<Object> handleBlogNotFoundException(BlogNotFoundException ex) {
        // Use dynamic error and message from the exception
        return ResponseHandler.errorResponseBuilder(
                ex.getError(),       // Get the error from the exception
                ex.getMessage(),     // Get the message from the exception
                HttpStatus.BAD_REQUEST
        );
    }
}