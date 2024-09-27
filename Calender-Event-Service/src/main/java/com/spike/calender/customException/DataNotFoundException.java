package com.spike.calender.customException;

public class DataNotFoundException extends Throwable{

	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String msg)
	{
		super(msg);
	}
}
