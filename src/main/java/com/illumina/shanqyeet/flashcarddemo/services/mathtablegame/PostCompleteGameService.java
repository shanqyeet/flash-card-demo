package com.illumina.shanqyeet.flashcarddemo.services.mathtablegame;

import com.illumina.shanqyeet.flashcarddemo.dtos.GameScoreCacheObject;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostCompleteGameResponse;
import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import com.illumina.shanqyeet.flashcarddemo.exceptions.GameSessionNotFoundException;
import com.illumina.shanqyeet.flashcarddemo.models.GameScoreEntity;
import com.illumina.shanqyeet.flashcarddemo.models.UserEntity;
import com.illumina.shanqyeet.flashcarddemo.repositories.GameScoreRepository;
import com.illumina.shanqyeet.flashcarddemo.services.helpers.mathtablegame.MathTableGameCache;
import com.illumina.shanqyeet.flashcarddemo.services.helpers.users.JwtUserDetailsExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class PostCompleteGameService {

    @Autowired
    private MathTableGameCache gameCache;

    @Autowired
    private GameScoreRepository gameScoreRepository;

    public PostCompleteGameResponse execute() throws GameSessionNotFoundException {
            UserEntity user = JwtUserDetailsExtractor.getUserFromContext();
            String userId = user.getId().toString();

            GameScoreCacheObject gameScoreCache = gameCache.getGameScores(userId);
            GameDifficulty.MathTableGame gameDifficulty  = GameDifficulty.MathTableGame
                    .fromString(gameCache.getGameDifficulty(userId));

            if(Objects.isNull(gameScoreCache) || Objects.isNull(gameDifficulty)){
                throw new GameSessionNotFoundException("There is no on-going game found, please start new game");
            }

            Integer totalScore = gameScoreCache.getLatestScore();
            Integer totalPenalty = gameScoreCache.getLatestPenalty();
            Long averageAnswerTimeMillis = calculateAverageAnswerTime(totalScore, totalPenalty, gameScoreCache.getTotalAnswerTimeInMillis());

            GameScoreEntity newGameScore = GameScoreEntity.builder()
                    .userId(user.getId())
                    .username(user.getUsername())
                    .gameDifficulty(gameDifficulty)
                    .score(totalScore)
                    .penalty(totalPenalty)
                    .averageAnswerTimeInMillis(averageAnswerTimeMillis)
                    .createdAt(LocalDateTime.now())
                    .build();

            try {
                gameScoreRepository.save(newGameScore);
            } catch (Exception e) {
                e.printStackTrace();
            }
            gameCache.clearCurrentGameData(userId);

            Double rateOfCorrectAnswer = Double.valueOf(totalScore/(totalScore+totalPenalty));
            return PostCompleteGameResponse.builder()
                    .averageAnswerTime(averageAnswerTimeMillis)
                    .correctAnswerRate(rateOfCorrectAnswer)
                    .totalScore(totalScore)
                    .build();
    }

    public Long calculateAverageAnswerTime(Integer totalScore, Integer totalPenalty, Long totalAnswerTimeInMillis){
        Integer totalGame =  totalScore + totalPenalty;
        if(totalGame < 1) return Long.valueOf(0);
        return totalAnswerTimeInMillis/totalGame;
    }
}
