package com.illumina.shanqyeet.flashcarddemo.enums;

import static com.illumina.shanqyeet.flashcarddemo.enums.ArithmeticOperators.*;

public final class GameDifficulty {
    public enum MathTableGame {
        EASY(new ArithmeticOperators[]{MINUS, PLUS}),
        MEDIUM(new ArithmeticOperators[]{PRODUCT,DIVIDE}),
        HARD(new ArithmeticOperators[]{MINUS, PLUS, PRODUCT, DIVIDE, MODULUS});

        private ArithmeticOperators[] operators;

        MathTableGame(ArithmeticOperators[] operators){
            this.operators = operators;
        }

        public ArithmeticOperators[] getOperators(){
            return operators;
        }
        public static boolean isEasyMedium(String difficulty){
            return EASY.name().equalsIgnoreCase(difficulty) || MEDIUM.name().equalsIgnoreCase(difficulty);
        }

        public static boolean isHard(String difficulty){
            return HARD.name().equalsIgnoreCase(difficulty);
        }

        public static GameDifficulty.MathTableGame fromString(String difficulty) {
            for(MathTableGame gameDifficulty : MathTableGame.values()) {
                if (gameDifficulty.name().equalsIgnoreCase(difficulty)) {
                    return gameDifficulty;
                }
            }
            return null;
        }
    }

}
