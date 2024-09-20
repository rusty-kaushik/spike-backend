package com.spike.Notes.ResponseHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseHandler {

   public ResponseEntity<Object> response(String message, HttpStatus status, Object Data){
       Map<String, Object> response = new HashMap<>();
       response.put("message", message);
       response.put("status", status);
       response.put("data", Data);
       return new ResponseEntity<>(response, status);
    }
   }


