package com.illumina.shanqyeet.flashcarddemo.enums;

public enum ArithmeticOperators {
    MINUS('-'),
    PLUS('+'),
    PRODUCT('*'),
    DIVIDE('/'),
    MODULUS('%');

    private Character operator;

    ArithmeticOperators(Character operator) {
        this.operator = operator;
    }

    public Character value(){
        return operator;
    }
}
