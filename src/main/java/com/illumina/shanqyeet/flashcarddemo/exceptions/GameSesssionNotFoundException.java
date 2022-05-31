package com.illumina.shanqyeet.flashcarddemo.exceptions;

public class GameSesssionNotFoundException extends Exception {
    public GameSesssionNotFoundException(){
        super();
    }

    public GameSesssionNotFoundException(final String message) {
        super(message);
    }
}
