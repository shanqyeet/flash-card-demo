package com.illumina.shanqyeet.flashcarddemo.services.mathtablegame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.illumina.shanqyeet.flashcarddemo.dtos.GameScoreCacheDto;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostChallengeResultRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostChallengeResultResponse;
import com.illumina.shanqyeet.flashcarddemo.models.UserEntity;
import com.illumina.shanqyeet.flashcarddemo.services.helpers.mathtablegame.MathTableGameCache;
import com.illumina.shanqyeet.flashcarddemo.services.helpers.users.JwtUserDetailsExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
public class PostChallengeResultService {

    @Autowired
    private MathTableGameCache gameCache;

    public PostChallengeResultResponse execute(PostChallengeResultRequest request) throws JsonProcessingException {
        UserEntity user = JwtUserDetailsExtractor.getUserFromContext();
        String userId = user.getId().toString();
        GameScoreCacheDto gameScoreCache = Optional.ofNullable(gameCache.getGameScores(userId))
                .orElse(new GameScoreCacheDto());
        Integer currentLatestScore = 0;
        Integer currentLatestPenalty = 0;
        Long currentTotalAnswerTime = Long.valueOf(0);

        if(Objects.nonNull(gameScoreCache)){
            currentLatestScore = gameScoreCache.getLatestScore();
            currentLatestPenalty = gameScoreCache.getLatestPenalty();
            currentTotalAnswerTime = gameScoreCache.getTotalAnswerTimeInMillis();
        }

        if (request.isPassed()){
            currentLatestScore += 1;
            gameScoreCache.setLatestScore(currentLatestScore);
        } else {
            currentLatestPenalty += 1;
            gameScoreCache.setLatestPenalty(currentLatestPenalty);
        }
        gameScoreCache.setTotalAnswerTimeInMillis(currentTotalAnswerTime + request.getAnswerTimeInMillis());
        gameCache.putGameScores(userId, gameScoreCache);

        return PostChallengeResultResponse.builder()
                .totalScore(currentLatestScore)
                .totalPenalty(currentLatestPenalty)
                .build();
    }
}
