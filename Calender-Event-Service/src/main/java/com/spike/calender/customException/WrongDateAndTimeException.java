package com.spike.calender.customException;

public class WrongDateAndTimeException extends Throwable{

	private static final long serialVersionUID = 1L;

	public WrongDateAndTimeException(String msg) {
		super(msg);
	}
}
