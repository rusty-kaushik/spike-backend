package com.spike.todo.exceptions;

public class ContactNotFoundException extends RuntimeException{
    public ContactNotFoundException(String msg) {
        super(msg);
    }
}
