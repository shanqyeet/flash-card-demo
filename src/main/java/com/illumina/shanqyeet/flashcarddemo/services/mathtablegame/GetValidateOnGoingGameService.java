package com.illumina.shanqyeet.flashcarddemo.services.mathtablegame;

import com.illumina.shanqyeet.flashcarddemo.dtos.GameScoreCacheObject;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.GetValidateOnGoingGameRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetValidateOnGoingGameResponse;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.illumina.shanqyeet.flashcarddemo.utils.Constants.GameStatus.CACHE_GAME_SESSION;

@Service
public class GetValidateOnGoingGameService {

//    @Autowired
//    private RedisTemplate redisTemplate;

    public GetValidateOnGoingGameResponse execute(GetValidateOnGoingGameRequest request){
        ConcurrentHashMap<String, GameScoreCacheObject> gameScoreMapCache = new ConcurrentHashMap<>();
        GameScoreCacheObject gameScore = gameScoreMapCache.get(request.getUserId() + CACHE_GAME_SESSION);
//        GameScoreCacheObject gameScoreCache = (GameScoreCacheObject) redisTemplate.opsForHash().get(request.getUserId(), CACHE_GAME_SESSION);
        return Objects.isNull(gameScore)
                ? GetValidateOnGoingGameResponse.builder().hasOnGoingGame(Boolean.FALSE).build()
                : GetValidateOnGoingGameResponse.builder().hasOnGoingGame(Boolean.TRUE).build();
    }
}
