package com.mail.JavaMail.customExceptions;

public class MailNotSentException extends Throwable{

	
	private static final long serialVersionUID = 1L;

	public MailNotSentException(String message)
	{
		super(message);
	}
}
