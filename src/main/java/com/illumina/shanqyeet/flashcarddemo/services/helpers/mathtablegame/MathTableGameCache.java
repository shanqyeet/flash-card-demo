package com.illumina.shanqyeet.flashcarddemo.services.helpers.mathtablegame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.illumina.shanqyeet.flashcarddemo.dtos.GameScoreCacheDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.illumina.shanqyeet.flashcarddemo.utils.Constants.*;

@Slf4j
@Component
public class MathTableGameCache {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    Gson gson;

    public GameScoreCacheDto getGameScores(String userId) {
        GameScoreCacheDto data = gson.fromJson(redisTemplate.opsForValue().get(userId + CACHE_GAME_SESSION), GameScoreCacheDto.class);
        log.info("GAME SCORE DATA: {}", data);
        return data;
    }

    public String getGameDifficulty(String userId) {
        String data =  gson.fromJson(redisTemplate.opsForValue().get(userId + CACHE_GAME_DIFFICULTY), String.class);
        log.info("GAME DIFFICULTY DATA: {}", data);
        return data;
    }

    public Map<String, Double> getNumPairFrequency(String userId) {
        Map<String, Double> data = gson.fromJson(redisTemplate.opsForValue().get(userId + CACHE_NUM_PAIR_FREQUENCY), new HashMap<String, Integer>().getClass());
        log.info("NUM PAIR DATA: {}", data);
        return data;
    }

    public void putGameScores(String userId, GameScoreCacheDto data) throws JsonProcessingException {
        redisTemplate.opsForValue().set(userId + CACHE_GAME_SESSION, gson.toJson(data));
    }

    public void putGameDifficulty(String userId, String data) throws JsonProcessingException {
        redisTemplate.opsForValue().set(userId + CACHE_GAME_DIFFICULTY, gson.toJson(data));
    }

    public void putNumPairFrequency(String userId, Map<String, Double> data) throws JsonProcessingException {
        redisTemplate.opsForValue().set(userId + CACHE_NUM_PAIR_FREQUENCY, gson.toJson(data));
    }

    public void clearCurrentGameData(String userId) {
        redisTemplate.delete(userId + CACHE_GAME_SESSION);
        redisTemplate.delete(userId + CACHE_GAME_DIFFICULTY);
        redisTemplate.delete(userId + CACHE_NUM_PAIR_FREQUENCY);
    }
}
