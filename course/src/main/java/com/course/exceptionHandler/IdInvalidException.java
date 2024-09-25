package com.course.exceptionHandler;

public class IdInvalidException extends RuntimeException {

	public IdInvalidException(String msg) {
		super(msg);
	}

}
