package com.spike.calender.customException;


public class PersistingException extends Throwable
{
	private static final long serialVersionUID = -7196522573608852677L;

	public PersistingException(String str)
	{
		super(str);
	}
	
}
