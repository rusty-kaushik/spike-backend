package com.spike.Notes.CustomExceptions;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(String message){
        super(message);
    }

}
