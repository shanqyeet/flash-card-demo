package com.illumina.shanqyeet.flashcarddemo.services.gamescorehistory;

import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetTopHundreScoresResponse;
import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import com.illumina.shanqyeet.flashcarddemo.models.GameScoreEntity;
import com.illumina.shanqyeet.flashcarddemo.repositories.GameScoreRepository;
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
        List<GameScoreEntity> too100ScoresHard = Optional.ofNullable(gameScoreRepository.findTop100ByGameDifficultyOrderByScoreDesc(GameDifficulty.MathTableGame.HARD)).orElse(new ArrayList<GameScoreEntity>());
        List<GameScoreEntity> too100ScoresMedium = Optional.ofNullable(gameScoreRepository.findTop100ByGameDifficultyOrderByScoreDesc(GameDifficulty.MathTableGame.MEDIUM)).orElse(new ArrayList<GameScoreEntity>());
        List<GameScoreEntity> too100ScoresEasy = Optional.ofNullable(gameScoreRepository.findTop100ByGameDifficultyOrderByScoreDesc(GameDifficulty.MathTableGame.EASY)).orElse(new ArrayList<GameScoreEntity>());

        Comparator<GameScoreEntity> scoreComparator = Comparator
                .comparingInt(GameScoreEntity::getScore).reversed()
                .thenComparing(GameScoreEntity::getAverageAnswerTimeInMillis)
                .thenComparing(GameScoreEntity::getPenalty);

        List<GameScoreEntity> too100ScoresHardSorted = too100ScoresHard.stream()
                .sorted(scoreComparator)
                .collect(Collectors.toList());
        List<GameScoreEntity> too100ScoresMediumSorted = too100ScoresMedium.stream()
                .sorted(scoreComparator)
                .collect(Collectors.toList());
        List<GameScoreEntity> too100ScoresEasySorted = too100ScoresEasy.stream()
                .sorted(scoreComparator)
                .collect(Collectors.toList());

        return GetTopHundreScoresResponse.builder()
                .hard(too100ScoresHardSorted)
                .medium(too100ScoresMediumSorted)
                .easy(too100ScoresEasySorted)
                .build();
    }
}
