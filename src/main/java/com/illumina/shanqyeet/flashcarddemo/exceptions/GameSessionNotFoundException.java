package com.illumina.shanqyeet.flashcarddemo.exceptions;

public class GameSessionNotFoundException extends Exception{
    public GameSessionNotFoundException(){
        super();
    }

    public GameSessionNotFoundException(final String message) {
        super(message);
    }
}
