package com.illumina.shanqyeet.flashcarddemo.exceptions;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(){
        super();
    }

    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
