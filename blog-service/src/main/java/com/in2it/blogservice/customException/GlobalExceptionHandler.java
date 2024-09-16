package com.in2it.blogservice.customException;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolationException;


@RestControllerAdvice
public class GlobalExceptionHandler {


	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(InfoMissingException.class)
	public  ResponseEntity<?> InfoMissingExceptionhandler(InfoMissingException ex ,WebRequest request){

   ExceptionStatus status=new ExceptionStatus(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));		
		
		return new ResponseEntity(status,HttpStatus.NOT_FOUND);

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(IdInvalidException.class)
	public  ResponseEntity<?>  idInvalidExceptionhandler(IdInvalidException ex ,WebRequest request){

		 ExceptionStatus status=new ExceptionStatus(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));		
			
			return new ResponseEntity(status,HttpStatus.NOT_FOUND);
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(UserNotFoundException.class)
	public  ResponseEntity<?> UserNotFoundExceptionhandler(UserNotFoundException ex, WebRequest request){
		
		 ExceptionStatus status=new ExceptionStatus(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));		
			
			return new ResponseEntity(status,HttpStatus.NOT_FOUND);
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(CommentServiceDownException.class)
	public  ResponseEntity<?> commentServiceDownException(CommentServiceDownException ex, WebRequest request){
		
		ExceptionStatus status=new ExceptionStatus(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));		
		
		return new ResponseEntity(status,HttpStatus.NOT_FOUND);
		
	}
	
	
	   @ExceptionHandler(ConstraintViolationException.class)
	    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e,  WebRequest request) {
	        if (isControllerException(e)) {
	        	ExceptionStatus status=new ExceptionStatus(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage(), request.getDescription(false));		
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
	    @SuppressWarnings("unchecked")
		@ExceptionHandler(LikeServiceDownException.class)
	    public  ResponseEntity<?> likeServiceDownException(LikeServiceDownException ex, WebRequest request){
			
			ExceptionStatus status=new ExceptionStatus(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));		
			
			return new ResponseEntity(status,HttpStatus.NOT_FOUND);
			
		}
}
