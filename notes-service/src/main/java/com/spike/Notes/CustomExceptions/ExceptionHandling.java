package com.spike.Notes.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<Object> runTimeException(RuntimeException ex){
        Map<String, Object> response= new HashMap<>();
        response.put("error","an error occurred");
        response.put("httpStatusCode", HttpStatus.BAD_REQUEST);
        response.put("message",ex.getMessage());
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @ExceptionHandler(NoteNotFoundException.class)
    private ResponseEntity<Object> noteNotFoundException(NoteNotFoundException ex){
        Map<String, Object> response= new HashMap<>();
        response.put("error","Note not found");
        response.put("httpStatusCode", HttpStatus.NOT_FOUND);
        response.put("message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }
}
