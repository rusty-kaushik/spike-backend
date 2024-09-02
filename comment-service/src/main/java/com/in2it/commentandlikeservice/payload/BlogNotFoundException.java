package com.in2it.commentandlikeservice.payload;

public class BlogNotFoundException extends RuntimeException{

	public BlogNotFoundException(String msg) {
		super(msg);
	}

	
}
