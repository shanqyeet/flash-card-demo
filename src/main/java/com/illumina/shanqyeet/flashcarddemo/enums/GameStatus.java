package com.illumina.shanqyeet.flashcarddemo.enums;

import com.illumina.shanqyeet.flashcarddemo.utils.Constants;

public final class GameStatus {
    public enum MultiplicationTableGame {
        NEW(Constants.GameStatus.NEW),
        IN_PROGRESS(Constants.GameStatus.IN_PROGRESS);

        private String status;

        MultiplicationTableGame(String status){
            this.status = status;
        }

        public String value(){
            return status;
        }

        public static GameStatus.MultiplicationTableGame fromString(String status) {
            for(GameStatus.MultiplicationTableGame gameStatus : GameStatus.MultiplicationTableGame.values()) {
                if (gameStatus.name().equalsIgnoreCase(status)) {
                    return gameStatus;
                }
            }
            return null;
        }
    }
}
