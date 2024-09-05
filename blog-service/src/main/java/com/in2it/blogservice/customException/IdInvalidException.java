package com.in2it.blogservice.customException;

@SuppressWarnings("serial")
public class IdInvalidException extends RuntimeException{
	
	public IdInvalidException(String msg) {
		
		super(msg);
	}

}
