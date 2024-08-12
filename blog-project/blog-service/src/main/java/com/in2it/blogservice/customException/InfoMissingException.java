package com.in2it.blogservice.customException;

@SuppressWarnings("serial")
public class InfoMissingException extends RuntimeException{

	public InfoMissingException(String msg) {
		super(msg);
	}
}
