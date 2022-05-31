package com.illumina.shanqyeet.flashcarddemo.exceptions;

public class GameSesssionNotFoundException extends Exception{
    private String message;

    public GameSesssionNotFoundException() {
        this.message = "There is no on-going game found, please start new game";
    }
}
