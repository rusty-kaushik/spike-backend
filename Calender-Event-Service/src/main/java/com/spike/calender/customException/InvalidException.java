package com.spike.calender.customException;

public class InvalidException extends Throwable{

	private static final long serialVersionUID = 1L;

	public InvalidException(String msg)
	{
		super(msg);
	}
}
