package com.illumina.shanqyeet.flashcarddemo.services.mathtablegame;

import com.illumina.shanqyeet.flashcarddemo.dtos.GameScoreCacheObject;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostCompleteGameRequest;
import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import com.illumina.shanqyeet.flashcarddemo.models.GameScoreEntity;
import com.illumina.shanqyeet.flashcarddemo.repositories.GameScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.illumina.shanqyeet.flashcarddemo.utils.Constants.GameStatus.*;

@Slf4j
@Service
public class PostCompleteGameService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private GameScoreRepository gameScoreRepository;

    public void execute(PostCompleteGameRequest request){
//        ConcurrentHashMap<String, GameScoreCacheObject> gameSessionCache = new ConcurrentHashMap<>();
        String userId = request.getUserId();

        GameScoreCacheObject gameScoreCache = (GameScoreCacheObject) redisTemplate.opsForHash().get(userId, CACHE_GAME_SESSION);
//        GameScoreCacheObject gameSession = gameScoreCache.get(userId + CACHE_GAME_SESSION);
        GameDifficulty.MultiplicationTableGame gameDifficulty  = GameDifficulty
                .MultiplicationTableGame
                .fromString(String.valueOf(redisTemplate.opsForHash().get(userId, CACHE_GAME_DIFFICULTY)));

        Integer totalScore = gameScoreCache.getLatestScore();
        Integer totalPenalty = gameScoreCache.getLatestPenalty();
        Long averageAnswerTimeMillis = calculateAverageAnswerTime(totalScore, totalPenalty, gameScoreCache.getTotalAnswerTimeInMillis());

        GameScoreEntity newGameScore = GameScoreEntity.builder()
//                .userId(UUID.randomUUID())
                .gameDifficulty(GameDifficulty.MultiplicationTableGame.EASY)
                .score(totalScore)
                .penalty(totalPenalty)
                .averageAnswerTimeInMillis(averageAnswerTimeMillis)
                .createdAt(LocalDateTime.now())
                .build();

        try {
            log.info("testing");
            gameScoreRepository.save(newGameScore);
        } catch (Exception e) {
            throw e;
        }

        redisTemplate.opsForHash().delete(userId, CACHE_GAME_DIFFICULTY);
        redisTemplate.opsForHash().delete(userId, CACHE_GAME_SESSION);
        redisTemplate.opsForHash().delete(userId, CACHE_NUM_PAIR_FREQUENCY);
    }

    public Long calculateAverageAnswerTime(Integer totalScore, Integer totalPenalty, Long totalAnswerTimeInMillis){
        return totalAnswerTimeInMillis/(totalScore+ totalPenalty);
    }
}
