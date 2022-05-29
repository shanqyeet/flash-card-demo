package com.illumina.shanqyeet.flashcarddemo.services.mathtablegame;

import com.illumina.shanqyeet.flashcarddemo.dtos.GameScoreCacheObject;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostChallengeResultRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostChallengeResultResponse;
import com.illumina.shanqyeet.flashcarddemo.services.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.illumina.shanqyeet.flashcarddemo.utils.Constants.GameStatus.CACHE_GAME_SESSION;

@Slf4j
@Service
public class PostChallengeResultService implements BaseService<PostChallengeResultRequest, PostChallengeResultResponse> {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PostChallengeResultResponse execute(PostChallengeResultRequest request) {
        log.info("REQUEST: " + request);
        String userId = request.getUserId();
       ConcurrentHashMap<String, GameScoreCacheObject> gameSessionMapCache = new ConcurrentHashMap<>();
//       GameScoreCacheObject gameScoreCache = Optional.ofNullable(gameSessionMapCache.get(userId + CACHE_GAME_SESSION)).orElse(new GameScoreCacheObject());
        GameScoreCacheObject gameScoreCache = Optional.ofNullable((GameScoreCacheObject) redisTemplate.opsForHash().get(userId, CACHE_GAME_SESSION))
                .orElse(new GameScoreCacheObject());
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
//        gameSessionMapCache.put(userId + CACHE_GAME_SESSION, gameScoreCache);
        redisTemplate.opsForHash().put(userId, CACHE_GAME_SESSION, gameScoreCache);

        return PostChallengeResultResponse.builder()
                .totalScore(currentLatestScore)
                .totalPenalty(currentLatestPenalty)
                .build();
    }
}
