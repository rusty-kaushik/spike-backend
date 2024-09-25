package com.mail.JavaMail.customExceptions;

@SuppressWarnings("serial")
public class FileHandlingException extends Throwable
{
	public FileHandlingException(String msg)
	{
		super(msg);
	}
}
