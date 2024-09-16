package com.in2it.blogservice.customException;

@SuppressWarnings("serial")
public class LikeServiceDownException extends Throwable{
	
	public LikeServiceDownException(String msg) 
	{
		super(msg);
	}
}
