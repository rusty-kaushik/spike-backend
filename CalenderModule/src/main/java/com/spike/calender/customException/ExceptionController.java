package com.spike.calender.customException;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionController {

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(FeignException.class)
	public  ResponseEntity<?> yFeignException(FeignException ex ,WebRequest request)
	{

		ExceptionProperties exceptionResponse=new ExceptionProperties(LocalDateTime.now(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));		
		
		return new ResponseEntity(exceptionResponse,HttpStatus.BAD_REQUEST);

	}
	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@ExceptionHandler(PersistingException.class)
//	public  ResponseEntity<?> persistingException(PersistingException ex ,WebRequest request)
//	{
//
//		ExceptionProperties exceptionResponse=new ExceptionProperties(LocalDateTime.now(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));		
//		
//		return new ResponseEntity(exceptionResponse,HttpStatus.BAD_REQUEST);
//
//	}
}
