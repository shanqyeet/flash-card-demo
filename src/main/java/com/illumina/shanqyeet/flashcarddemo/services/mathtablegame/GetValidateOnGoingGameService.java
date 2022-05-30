package com.illumina.shanqyeet.flashcarddemo.services.mathtablegame;

import com.illumina.shanqyeet.flashcarddemo.dtos.GameScoreCacheObject;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.GetValidateOnGoingGameRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetValidateOnGoingGameResponse;
import com.illumina.shanqyeet.flashcarddemo.services.helpers.mathtablegame.MathTableGameCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class GetValidateOnGoingGameService {

    @Autowired
    private MathTableGameCache gameCache;

    public GetValidateOnGoingGameResponse execute(GetValidateOnGoingGameRequest request){
        try {
            GameScoreCacheObject gameScore = gameCache.getGameScores(request.getUserId());
            if (Objects.nonNull(gameScore)) {
                if (gameScore.getLatestPenalty() > 0 || gameScore.getLatestScore() > 0) {
                    return GetValidateOnGoingGameResponse.builder().hasOnGoingGame(Boolean.TRUE).build();
                }
            }
            return  GetValidateOnGoingGameResponse.builder().hasOnGoingGame(Boolean.FALSE).build();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
