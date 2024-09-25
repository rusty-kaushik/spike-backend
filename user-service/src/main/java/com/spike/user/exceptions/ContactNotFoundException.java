package com.spike.user.exceptions;

public class ContactNotFoundException extends RuntimeException{
    public ContactNotFoundException(String msg) {
        super(msg);
    }
}
