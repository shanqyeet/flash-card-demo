package com.illumina.shanqyeet.flashcarddemo.enums;

import java.util.List;

public enum GameDifficulty {
    EASY(List.of('+','-')),
    MEDIUM(List.of('*','/')),
    HARD(List.of('+','-','*','/'));

    private List<Character> operators;

    GameDifficulty(List<Character> operators){
        this.operators = operators;
    }
}
