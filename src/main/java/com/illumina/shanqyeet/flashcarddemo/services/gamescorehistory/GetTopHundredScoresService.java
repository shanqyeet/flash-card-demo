package com.illumina.shanqyeet.flashcarddemo.services.gamescorehistory;

import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetTopHundreScoresResponse;
import com.illumina.shanqyeet.flashcarddemo.models.GameScoreEntity;
import com.illumina.shanqyeet.flashcarddemo.repositories.GameScoreRepository;
import com.illumina.shanqyeet.flashcarddemo.utils.GameDifficultyComparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GetTopHundredScoresService {

    @Autowired
    private GameScoreRepository gameScoreRepository;
    public GetTopHundreScoresResponse execute(){
        try {
            List<GameScoreEntity> too100Scores = Optional.ofNullable(gameScoreRepository.findTop100ByOrderByScoreDesc()).orElse(new ArrayList<GameScoreEntity>());
            too100Scores.stream()
                    .sorted((score1, score2) -> new GameDifficultyComparator().compare(score1.getGameDifficulty().name(), score2.getGameDifficulty().name()))
                    .sorted(Comparator.comparing(GameScoreEntity::getAverageAnswerTimeInMillis))
                    .collect(Collectors.toList());
            return GetTopHundreScoresResponse.builder()
                    .top100Scores(too100Scores)
                    .build();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
