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
	public  ResponseEntity<?> feignException(FeignException ex ,WebRequest request)
	{

		ExceptionProperties exceptionResponse=new ExceptionProperties(LocalDateTime.now(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));		
		
		return new ResponseEntity(exceptionResponse,HttpStatus.BAD_REQUEST);

	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(WrongDateAndTimeException.class)
	public  ResponseEntity<?> wrongDateAndTimeException(WrongDateAndTimeException ex ,WebRequest request)
	{

		ExceptionProperties exceptionResponse=new ExceptionProperties(LocalDateTime.now(), HttpStatus.BAD_REQUEST.getReasonPhrase()  , HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));		
		
		return new ResponseEntity(exceptionResponse,HttpStatus.BAD_REQUEST);

	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(DataNotFoundException.class)
	public  ResponseEntity<?> dataNotFoundException(DataNotFoundException ex ,WebRequest request)
	{

		ExceptionProperties exceptionResponse=new ExceptionProperties(LocalDateTime.now(), HttpStatus.NOT_FOUND.getReasonPhrase()  , HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));		
		
		return new ResponseEntity(exceptionResponse,HttpStatus.NOT_FOUND);

	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(InvalidException.class)
	public  ResponseEntity<?> invalidException(InvalidException ex ,WebRequest request)
	{

		ExceptionProperties exceptionResponse=new ExceptionProperties(LocalDateTime.now(), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase()  , HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage(), request.getDescription(false));		
		
		return new ResponseEntity(exceptionResponse,HttpStatus.NOT_ACCEPTABLE);

	}

}
