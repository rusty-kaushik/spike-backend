package com.in2it.spiketicket.service.exception;

public class TicketNotFoundException extends RuntimeException {
	
	public TicketNotFoundException(String msg) {
		super(msg);
	}

}
