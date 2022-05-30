package com.illumina.shanqyeet.flashcarddemo.enums;

import static com.illumina.shanqyeet.flashcarddemo.utils.Constants.*;

public enum ArithmeticOperators {
    MINUS(MINUS_CHAR),
    PLUS(PLUS_CHAR),
    PRODUCT(MULTIPLY_CHAR),
    DIVIDE(DIVIDE_CHAR),
    MODULUS(MODULUS_CHAR);

    private Character operator;

    ArithmeticOperators(Character operator) {
        this.operator = operator;
    }

    public Character value(){
        return operator;
    }
}
