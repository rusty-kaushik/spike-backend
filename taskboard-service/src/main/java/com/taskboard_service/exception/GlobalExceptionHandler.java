package com.taskboard_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolationException;


@RestControllerAdvice
public class GlobalExceptionHandler {


	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(TaskboadNotFoundException.class)
	public  ResponseEntity<?> taskboadNotFoundException(TaskboadNotFoundException ex ,WebRequest request){

   ExceptionStatus status=new ExceptionStatus(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());		
		
		return new ResponseEntity(status,HttpStatus.NOT_FOUND);

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(DetailsNotFoundException.class)
	public  ResponseEntity<?>  detailsNotFoundException(DetailsNotFoundException ex ,WebRequest request){

		 ExceptionStatus status=new ExceptionStatus( HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());		
			
			return new ResponseEntity(status,HttpStatus.NOT_FOUND);
		
	}
	
	
	   @ExceptionHandler(ConstraintViolationException.class)
	    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e,  WebRequest request) {
	        if (isControllerException(e)) {
	        	ExceptionStatus status=new ExceptionStatus( HttpStatus.NOT_FOUND.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());		
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(status);
	        } else {
	       
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
	        }
	    }
	    
	    
	    private boolean isControllerException(ConstraintViolationException e) {
	        StackTraceElement[] stackTrace = e.getStackTrace();
	        for (StackTraceElement element : stackTrace) {
	            // Check if the class name contains "controller" (case insensitive)
	            if (element.getClassName().toLowerCase().contains("controller")) {
	                return true;
	            }
	        }
	        return false;
	    }
	 
}
