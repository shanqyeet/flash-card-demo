package com.illumina.shanqyeet.flashcarddemo.services.mathtablegame;

import com.illumina.shanqyeet.flashcarddemo.dtos.GameScoreCacheObject;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.GetNextChallengeRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetNextChallengeResponse;
import com.illumina.shanqyeet.flashcarddemo.enums.ArithmeticOperators;
import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import com.illumina.shanqyeet.flashcarddemo.enums.GameStatus;
import com.illumina.shanqyeet.flashcarddemo.models.UserEntity;
import com.illumina.shanqyeet.flashcarddemo.services.BaseService;
import com.illumina.shanqyeet.flashcarddemo.services.helpers.mathtablegame.MathTableGameCache;
import com.illumina.shanqyeet.flashcarddemo.services.helpers.users.JwtUserDetailsExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.illumina.shanqyeet.flashcarddemo.utils.Constants.*;
import static java.util.Objects.isNull;

@Slf4j
@Service
public class GetNextChallengeService implements BaseService<GetNextChallengeRequest, GetNextChallengeResponse> {

    @Autowired
    private MathTableGameCache gameCache;

    @Autowired
    private Random randomNumGenerator;

    @Override
    public GetNextChallengeResponse execute(GetNextChallengeRequest request) throws Exception {
        UserEntity user = JwtUserDetailsExtractor.getUserFromContext();
        String userId = user.getId().toString();
        GameDifficulty.MathTableGame gameDifficulty = request.getGameDifficulty();

        try {
            if(isNewGame(request)){
                gameCache.putGameScores(userId, new GameScoreCacheObject());
                gameCache.putGameDifficulty(userId, gameDifficulty.name());
            } else {
                String cachedDifficulty = gameCache.getGameDifficulty(userId);
                gameDifficulty = GameDifficulty.MathTableGame.fromString(cachedDifficulty);
            }

                Map<String, Double> numPairFrequencyMap = Optional.ofNullable(gameCache.getNumPairFrequency(userId)).orElse(new HashMap<>());
                log.info("NUM PAIR FREQUENCY MAP");
                log.info(numPairFrequencyMap.toString());
                numPairFrequencyMap.forEach((key, value) -> {
                    log.info("[{} : {}]", key, value);
                });

        AbstractMap.SimpleEntry<Integer, Integer> generatedNumPair = generateNumberPairWithAppearanceFrequencyCheck(numPairFrequencyMap, userId, gameDifficulty.name());
        Character chosenOperator = getRandomOperator(generatedNumPair.getValue(), gameDifficulty);

        return GetNextChallengeResponse.builder()
                .firstNum(generatedNumPair.getKey())
                .secondNum(generatedNumPair.getValue())
                .operator(chosenOperator)
                .result(calculateExpectedResult(generatedNumPair.getKey(), generatedNumPair.getValue(), chosenOperator))
                .build();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Integer calculateExpectedResult(Integer firstNum, Integer secondNum, Character operator) throws Exception {
        Integer result = 0;
        switch(operator) {
            case MINUS_CHAR:
                result = firstNum - secondNum;
                break;
            case PLUS_CHAR:
                result = firstNum + secondNum;
                break;
            case DIVIDE_CHAR:
                result = firstNum / secondNum;
                break;
            case MULTIPLY_CHAR:
                result = firstNum * secondNum;
                break;
            case MODULUS_CHAR:
                result = firstNum % secondNum;
                break;
            default:
                throw new Exception("Non allowed arithmetic operator");
        }
        return result;

    }

    private Character getRandomOperator(Integer secondNum, GameDifficulty.MathTableGame gameDifficulty ){
        ArithmeticOperators[] possibleOperators = gameDifficulty.getOperators();
        int operatorSize = possibleOperators.length;
        int randomOperatorIdx = new Random()
                .ints(0, operatorSize - 1)
                .findFirst()
                .getAsInt();
        Character possibleChar = possibleOperators[randomOperatorIdx].value();

        //* Any number cannot be divided or modulus by zero
        if(secondNum < 1 && (possibleChar.equals(DIVIDE_CHAR) || possibleChar.equals(MODULUS_CHAR))){
            getRandomOperator(secondNum, gameDifficulty);
        }
        return possibleChar;
    }

    private AbstractMap.SimpleEntry<Integer, Integer> generateNumberPairWithAppearanceFrequencyCheck(
            Map<String, Double> numPairFrequencyMap,
            String userId,
            String gameDifficulty
    ) throws Exception {
        Integer firstNum = generateRandomZeroToTwelve();
        Integer secondNum = generateRandomZeroToTwelve();
        String numPairCacheKey = "" + firstNum + secondNum;
        Double numPairFrequency = numPairFrequencyMap.get(numPairCacheKey);
        if(isNull(numPairFrequency)){
          return updateNumPairFrequencyMapAndReturnNumPair(numPairFrequencyMap, userId, numPairCacheKey, Double.valueOf(1.0), firstNum, secondNum);
        } else {
            boolean isEasyMedium = GameDifficulty.MathTableGame.isEasyMedium(gameDifficulty);
            boolean isHard = GameDifficulty.MathTableGame.isHard(gameDifficulty);
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
            Map<String, Double> numPairFrequencyMap,
            String userId,
            String numPairCacheKey,
            Double newNumPairFrequency,
            Integer firstNum,
            Integer secondNum
    ) throws Exception {
        numPairFrequencyMap.put(numPairCacheKey, newNumPairFrequency);
        gameCache.putNumPairFrequency(userId, numPairFrequencyMap);
        return new AbstractMap.SimpleEntry<>(firstNum, secondNum);
    }

    private boolean isNewGame(GetNextChallengeRequest request){
        GameStatus.MathTableGame gameStatus = request.getGameStatus();
        return gameStatus.equals(GameStatus.MathTableGame.NEW);
    }
}
