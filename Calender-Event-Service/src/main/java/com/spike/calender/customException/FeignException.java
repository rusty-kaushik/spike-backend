package com.spike.calender.customException;

public class FeignException extends Throwable
{
	private static final long serialVersionUID = 1L;

	public FeignException(String str)
	{
		super(str);
	}
	
}
