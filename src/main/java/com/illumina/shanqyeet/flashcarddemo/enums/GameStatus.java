package com.illumina.shanqyeet.flashcarddemo.enums;

import static com.illumina.shanqyeet.flashcarddemo.utils.Constants.STATUS_IN_PROGRESS;
import static com.illumina.shanqyeet.flashcarddemo.utils.Constants.STATUS_NEW;

public final class GameStatus {
    public enum MathTableGame {
        NEW(STATUS_NEW),
        IN_PROGRESS(STATUS_IN_PROGRESS);

        private String status;

        MathTableGame(String status){
            this.status = status;
        }

        public String value(){
            return status;
        }

        public static GameStatus.MathTableGame fromString(String status) {
            for(GameStatus.MathTableGame gameStatus : GameStatus.MathTableGame.values()) {
                if (gameStatus.name().equalsIgnoreCase(status)) {
                    return gameStatus;
                }
            }
            return null;
        }
    }
}
