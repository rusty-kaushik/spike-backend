package com.in2it.commentandlikeservice.payload;

@SuppressWarnings("serial")
public class InfoMissingException extends RuntimeException{

	public InfoMissingException(String msg) {
		super(msg);
	}
	
}
