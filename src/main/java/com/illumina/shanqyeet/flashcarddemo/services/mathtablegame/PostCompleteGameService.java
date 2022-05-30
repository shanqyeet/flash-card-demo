package com.illumina.shanqyeet.flashcarddemo.services.mathtablegame;

import com.illumina.shanqyeet.flashcarddemo.dtos.GameScoreCacheObject;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostCompleteGameRequest;
import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import com.illumina.shanqyeet.flashcarddemo.models.GameScoreEntity;
import com.illumina.shanqyeet.flashcarddemo.repositories.GameScoreRepository;
import com.illumina.shanqyeet.flashcarddemo.services.helpers.mathtablegame.MathTableGameCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class PostCompleteGameService {

    @Autowired
    private MathTableGameCache gameCache;

    @Autowired
    private GameScoreRepository gameScoreRepository;

    public void execute(PostCompleteGameRequest request){

        try {
            String userId = request.getUserId();

            GameScoreCacheObject gameScoreCache = gameCache.getGameScores(userId);
            GameDifficulty.MathTableGame gameDifficulty  = GameDifficulty.MathTableGame
                    .fromString(gameCache.getGameDifficulty(userId));

            Integer totalScore = gameScoreCache.getLatestScore();
            Integer totalPenalty = gameScoreCache.getLatestPenalty();
            Long averageAnswerTimeMillis = calculateAverageAnswerTime(totalScore, totalPenalty, gameScoreCache.getTotalAnswerTimeInMillis());

            GameScoreEntity newGameScore = GameScoreEntity.builder()
    //                .userId(UUID.randomUUID())
                    .gameDifficulty(GameDifficulty.MathTableGame.EASY)
                    .score(totalScore)
                    .penalty(totalPenalty)
                    .averageAnswerTimeInMillis(averageAnswerTimeMillis)
                    .createdAt(LocalDateTime.now())
                    .build();

            gameScoreRepository.save(newGameScore);
            gameCache.clearCurrentGameData(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public Long calculateAverageAnswerTime(Integer totalScore, Integer totalPenalty, Long totalAnswerTimeInMillis){
        Integer totalGame =  totalScore + totalPenalty;
        if(totalGame < 1) return Long.valueOf(0);
        return totalAnswerTimeInMillis/totalGame;
    }
}
