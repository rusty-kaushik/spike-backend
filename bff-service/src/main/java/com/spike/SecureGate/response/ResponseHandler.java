package com.spike.SecureGate.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    // Method for building success responses
    public static ResponseEntity<Object> responseBuilder(String message, HttpStatus httpStatus, Object responseObject){
        Map<String, Object> response = new HashMap<>();
        response.put("message",message);
        response.put("data",responseObject);
        response.put("httpStatus",httpStatus);
        return new ResponseEntity<>(response,httpStatus);
    }

    // Method for building error responses with dynamic error and message
    public static ResponseEntity<Object> errorResponseBuilder(String error, String message, HttpStatus httpStatus) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", error);  // Use dynamic error
        response.put("httpStatusCode", httpStatus.value());
        response.put("message", message);  // Use dynamic message
        return new ResponseEntity<>(response, httpStatus);
    }
}