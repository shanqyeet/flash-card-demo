package com.illumina.shanqyeet.flashcarddemo.services.mathtablegame;

import com.illumina.shanqyeet.flashcarddemo.dtos.GameScoreCacheObject;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.GetNextChallengeRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetNextChallengeResponse;
import com.illumina.shanqyeet.flashcarddemo.enums.ArithmeticOperators;
import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import com.illumina.shanqyeet.flashcarddemo.enums.GameStatus;
import com.illumina.shanqyeet.flashcarddemo.services.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static com.illumina.shanqyeet.flashcarddemo.utils.Constants.GameStatus.*;
import static java.util.Objects.isNull;

@Slf4j
@Service
public class GetNextChallengeService implements BaseService<GetNextChallengeRequest, GetNextChallengeResponse> {

    @Autowired
    private RedisTemplate redisTemplate;
    private static Random randomNumGenerator;

    @Override
    public GetNextChallengeResponse execute(GetNextChallengeRequest request) {
        log.info("############ REQUEST ###############");
        log.info(String.valueOf(request));
        ConcurrentHashMap<String, GameScoreCacheObject> gameScoreMapCache = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, HashMap<String, Integer>> numPairFrequencyMapCache = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, String> gameDifficultyMapCache = new ConcurrentHashMap<>();

        String userId = request.getUserId();
        GameDifficulty.MultiplicationTableGame gameDifficulty = request.getGameDifficulty();
        if(isNewGame(request)){
            redisTemplate.opsForHash().put(userId, CACHE_GAME_SESSION, new GameScoreCacheObject());
            redisTemplate.opsForHash().put(userId, CACHE_GAME_DIFFICULTY, gameDifficulty.toString());
//            gameScoreMapCache.put(userId + CACHE_GAME_SESSION, new GameScoreCacheObject());
//            gameDifficultyMapCache.put(userId + CACHE_GAME_DIFFICULTY, gameDifficulty.toString());
        } else {
            String cachedDifficulty = (String) redisTemplate.opsForHash().get(userId, CACHE_GAME_DIFFICULTY);
//            String cachedDifficulty = gameDifficultyMapCache.get(userId + CACHE_GAME_DIFFICULTY);
            gameDifficulty = GameDifficulty.MultiplicationTableGame.fromString(cachedDifficulty);
        }

//        HashMap<String, Integer> numPairFrequencyMap = (HashMap<String, Integer>) redisTemplate.opsForHash().get(userId, CACHE_NUM_PAIR_FREQUENCY);
        HashMap<String, Integer> numPairFrequencyMap = Optional.ofNullable(numPairFrequencyMapCache.get(userId + CACHE_NUM_PAIR_FREQUENCY)).orElse(new HashMap<>());
        AbstractMap.SimpleEntry<Integer, Integer> generatedNumPair = generateNumberPairWithAppearanceFrequencyCheck(numPairFrequencyMap, userId, gameDifficulty.name());

        Character chosenOperator = getRandomOperator(gameDifficulty);

        return GetNextChallengeResponse.builder()
                .firstNum(generatedNumPair.getKey())
                .secondNum(generatedNumPair.getValue())
                .operator(chosenOperator)
                .result(calculateExpectedResult(generatedNumPair.getKey(), generatedNumPair.getValue(), chosenOperator))
                .build();
    }

    private Integer calculateExpectedResult(Integer firstNum, Integer secondNum, Character operator){
        Integer result = 0;
        switch(operator) {
            case '-':
                result = firstNum - secondNum;
                break;
            case '+':
                result = firstNum + secondNum;
                break;

            case '/':
                result = firstNum / secondNum;
                break;
            case '*':
                result = firstNum * secondNum;
                break;
            case '%':
                result = firstNum % secondNum;
                break;
            default: result = null;
        }
        return result;

    }

    private Character getRandomOperator(GameDifficulty.MultiplicationTableGame gameDifficulty ){
        ArithmeticOperators[] possibleOperators = gameDifficulty.getOperators();
        int operatorSize = possibleOperators.length;
        int randomOpereratorIdx = new Random()
                .ints(0, operatorSize - 1)
                .findFirst()
                .getAsInt();
        return possibleOperators[randomOpereratorIdx].value();
    }

    private AbstractMap.SimpleEntry<Integer, Integer> generateNumberPairWithAppearanceFrequencyCheck(
            HashMap<String, Integer> numPairFrequencyMap,
            String userId,
            String gameDifficulty
    ){
        Integer firstNum = generateRandomZeroToTwelve();
        Integer secondNum = generateRandomZeroToTwelve();
        String numPairCacheKey = "" + firstNum + secondNum;
        Integer numPairFrequency = numPairFrequencyMap.get(numPairCacheKey);
        if(isNull(numPairFrequency)){
          return updateNumPairFrequencyMapAndReturnNumPair(numPairFrequencyMap, userId, numPairCacheKey, 1, firstNum, secondNum);
        } else {
            boolean isEasyMedium = GameDifficulty.MultiplicationTableGame.isEasyMedium(gameDifficulty);
            boolean isHard = GameDifficulty.MultiplicationTableGame.isHard(gameDifficulty);
            if(isEasyMedium && numPairFrequency <= NUMPAIR_MAX_FREQUENCY_EASY_MEDIUM) {
                return updateNumPairFrequencyMapAndReturnNumPair(numPairFrequencyMap, userId, numPairCacheKey, numPairFrequency + 1, firstNum, secondNum);
            } else if (isHard && numPairFrequency <= NUMPAIR_MAX_FREQUENCY_HARD){
                return updateNumPairFrequencyMapAndReturnNumPair(numPairFrequencyMap, userId, numPairCacheKey, numPairFrequency + 1, firstNum, secondNum);
            } else {
                return generateNumberPairWithAppearanceFrequencyCheck(numPairFrequencyMap, userId, gameDifficulty);
            }
        }
    }

    private Integer generateRandomZeroToTwelve(){
        log.info("RANDOM GENERATOR MIN: " + RANDOM_GENERATOR_MIN);
        log.info("RANDOM GENERATOR MAX: " + RANDOM_GENERATOR_MAX);
        return new Random()
            .ints(RANDOM_GENERATOR_MIN, RANDOM_GENERATOR_MAX)
            .findFirst()
            .getAsInt();
    }

    private AbstractMap.SimpleEntry<Integer, Integer> updateNumPairFrequencyMapAndReturnNumPair(
            HashMap<String, Integer> numPairFrequencyMap,
            String userId,
            String numPairCacheKey,
            Integer newNumPairFrequency,
            Integer firstNum,
            Integer secondNum
    ){
        numPairFrequencyMap.put(numPairCacheKey, newNumPairFrequency);
//        ConcurrentHashMap<String, HashMap<String, Integer>> numPairFrequencyMapCache = new ConcurrentHashMap<>();
//        numPairFrequencyMapCache.put(userId + CACHE_NUM_PAIR_FREQUENCY, numPairFrequencyMap);
        redisTemplate.opsForHash().put(userId, CACHE_NUM_PAIR_FREQUENCY, numPairFrequencyMap.toString());
        return new AbstractMap.SimpleEntry<>(firstNum, secondNum);
    }

    private boolean isNewGame(GetNextChallengeRequest request){
        GameStatus.MultiplicationTableGame gameStatus = request.getGameStatus();
        log.info("##################");
        log.info(gameStatus.value());
        return gameStatus.equals(GameStatus.MultiplicationTableGame.NEW);
    }
}
