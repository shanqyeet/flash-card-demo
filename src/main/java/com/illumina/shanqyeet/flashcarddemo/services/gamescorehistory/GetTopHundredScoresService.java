package com.illumina.shanqyeet.flashcarddemo.services.gamescorehistory;

import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetTopHundreScoresResponse;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetTopHundreScoresResponse.TopScoresDto;
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
        List<GameScoreEntity> top100ScoresHard = Optional.ofNullable(gameScoreRepository.findTop100ByGameDifficultyOrderByScoreDesc(GameDifficulty.MathTableGame.HARD)).orElse(new ArrayList<GameScoreEntity>());
        List<GameScoreEntity> top100ScoresMedium = Optional.ofNullable(gameScoreRepository.findTop100ByGameDifficultyOrderByScoreDesc(GameDifficulty.MathTableGame.MEDIUM)).orElse(new ArrayList<GameScoreEntity>());
        List<GameScoreEntity> top100ScoresEasy = Optional.ofNullable(gameScoreRepository.findTop100ByGameDifficultyOrderByScoreDesc(GameDifficulty.MathTableGame.EASY)).orElse(new ArrayList<GameScoreEntity>());

        Comparator<GameScoreEntity> scoreComparator = Comparator
                .comparingInt(GameScoreEntity::getScore).reversed()
                .thenComparing(GameScoreEntity::getAverageAnswerTimeInMillis)
                .thenComparing(GameScoreEntity::getPenalty);

        List<TopScoresDto> too100ScoresHardSorted = sortAndMapEntityToDto(top100ScoresHard, scoreComparator);
        List<TopScoresDto> too100ScoresMediumSorted = sortAndMapEntityToDto(top100ScoresMedium, scoreComparator);
        List<TopScoresDto> too100ScoresEasySorted = sortAndMapEntityToDto(top100ScoresEasy, scoreComparator);

        return GetTopHundreScoresResponse.builder()
                .hard(too100ScoresHardSorted)
                .medium(too100ScoresMediumSorted)
                .easy(too100ScoresEasySorted)
                .build();
    }

    public List<TopScoresDto> sortAndMapEntityToDto(List<GameScoreEntity> scores, Comparator<GameScoreEntity> comparator){
        return scores.stream()
                .sorted(comparator)
                .map(obj -> {
                    return TopScoresDto.builder()
                        .id(obj.getId())
                        .username(obj.getUsername())
                        .averageAnswerTimeInMillis(obj.getAverageAnswerTimeInMillis())
                        .score(obj.getScore())
                        .penalty(obj.getPenalty())
                        .build();
                }).collect(Collectors.toList());
    }
}
