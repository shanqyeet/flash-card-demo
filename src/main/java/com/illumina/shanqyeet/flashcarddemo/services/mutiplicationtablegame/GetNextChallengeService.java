package com.illumina.shanqyeet.flashcarddemo.services.mutiplicationtablegame;

import com.illumina.shanqyeet.flashcarddemo.dtos.requests.GetNextChallengeRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetNextChallengeResponse;
import com.illumina.shanqyeet.flashcarddemo.enums.ArithmeticOperators;
import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import com.illumina.shanqyeet.flashcarddemo.enums.GameStatus;
import com.illumina.shanqyeet.flashcarddemo.services.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.illumina.shanqyeet.flashcarddemo.utils.Constants.GameStatus.*;
import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class GetNextChallengeService implements BaseService<GetNextChallengeRequest, GetNextChallengeResponse> {

    @Autowired
    private final RedisTemplate redisTemplate;
    private final Random randomNumGenerator;

    @Override
    public GetNextChallengeResponse execute(GetNextChallengeRequest request) {
        String userId = request.getUserId().toString();
        GameDifficulty.MultiplicationTableGame gameDifficulty = request.getGameDifficulty();
        if(isNewGame(request)){
            redisTemplate.opsForHash().put(userId, CACHE_GAME_SESSION, new ArrayList().toString());
            redisTemplate.opsForHash().put(userId, CACHE_GAME_DIFFICULTY, gameDifficulty.toString());
        } else {
            String cachedDifficulty = (String) redisTemplate.opsForHash().get(userId, CACHE_GAME_DIFFICULTY);
            gameDifficulty = GameDifficulty.MultiplicationTableGame.fromString(cachedDifficulty);
        }

        HashMap<String, Integer> numPairFrequencyMap = (HashMap<String, Integer>) redisTemplate.opsForHash().get(userId, CACHE_NUM_PAIR_FREQUENCY);
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
        return switch(operator) {
            case '-' -> firstNum - secondNum;
            case '+' -> firstNum + secondNum;
            case '/' -> firstNum / secondNum;
            case '*' -> firstNum * secondNum;
            case '%' -> firstNum % secondNum;
            default -> null;
        };

    }

    private Character getRandomOperator(GameDifficulty.MultiplicationTableGame gameDifficulty ){
        ArithmeticOperators[] possibleOperators = gameDifficulty.getOperators();
        int operatorSize = possibleOperators.length;
        int randomOpereratorIdx =  randomNumGenerator
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
        return randomNumGenerator
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
        redisTemplate.opsForHash().put(userId, CACHE_NUM_PAIR_FREQUENCY, numPairFrequencyMap.toString());
        return new AbstractMap.SimpleEntry<>(firstNum, secondNum);
    }

    private boolean isNewGame(GetNextChallengeRequest request){
        GameStatus.MultiplicationTableGame gameStatus = request.getGameStatus();
        return gameStatus.equals(GameStatus.MultiplicationTableGame.NEW);
    }
}
