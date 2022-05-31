package com.illumina.shanqyeet.flashcarddemo.services.gamescorehistory;

import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetTopHundreScoresResponse;
import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import com.illumina.shanqyeet.flashcarddemo.models.GameScoreEntity;
import com.illumina.shanqyeet.flashcarddemo.repositories.GameScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty.MathTableGame.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GetTopHundredScoresServiceTest {

    @Mock
    private GameScoreRepository gameScoreRepositoryMock;
    @InjectMocks
    private GetTopHundredScoresService getTopHundredScoresService;

    @Test
    public void whenGameScoresAreReturnedCorrectOrder(){
        // GIVEN
        GameScoreEntity gameScore1 = GameScoreEntity.builder()
                .id(Long.valueOf(1))
                .score(180)
                .gameDifficulty(GameDifficulty.MathTableGame.MEDIUM)
                .averageAnswerTimeInMillis(Long.valueOf(50000))
                .penalty(0)
                .createdAt(LocalDateTime.now())
                .build();

        GameScoreEntity gameScore2 = GameScoreEntity.builder()
                .id(Long.valueOf(2))
                .score(100)
                .gameDifficulty(GameDifficulty.MathTableGame.MEDIUM)
                .averageAnswerTimeInMillis(Long.valueOf(30000))
                .penalty(0)
                .createdAt(LocalDateTime.now())
                .build();

        GameScoreEntity gameScore3 = GameScoreEntity.builder()
                .id(Long.valueOf(3))
                .score(180)
                .gameDifficulty(HARD)
                .averageAnswerTimeInMillis(Long.valueOf(30000))
                .penalty(1)
                .createdAt(LocalDateTime.now())
                .build();

        GameScoreEntity gameScore4 = GameScoreEntity.builder()
                .id(Long.valueOf(4))
                .score(120)
                .gameDifficulty(HARD)
                .averageAnswerTimeInMillis(Long.valueOf(41000))
                .penalty(10)
                .createdAt(LocalDateTime.now())
                .build();

        GameScoreEntity gameScore5 = GameScoreEntity.builder()
                .id(Long.valueOf(5))
                .score(110)
                .gameDifficulty(GameDifficulty.MathTableGame.EASY)
                .averageAnswerTimeInMillis(Long.valueOf(30000))
                .penalty(0)
                .createdAt(LocalDateTime.now())
                .build();

        GameScoreEntity gameScore6 = GameScoreEntity.builder()
                .id(Long.valueOf(6))
                .score(112)
                .gameDifficulty(GameDifficulty.MathTableGame.EASY)
                .averageAnswerTimeInMillis(Long.valueOf(30000))
                .penalty(20)
                .createdAt(LocalDateTime.now())
                .build();

        GameScoreEntity gameScore7 = GameScoreEntity.builder()
                .id(Long.valueOf(7))
                .score(180)
                .gameDifficulty(HARD)
                .averageAnswerTimeInMillis(Long.valueOf(10000))
                .penalty(1)
                .createdAt(LocalDateTime.now())
                .build();

        GameScoreEntity gameScore8 = GameScoreEntity.builder()
                .id(Long.valueOf(8))
                .score(112)
                .gameDifficulty(GameDifficulty.MathTableGame.EASY)
                .averageAnswerTimeInMillis(Long.valueOf(30000))
                .penalty(30)
                .createdAt(LocalDateTime.now())
                .build();


        List<GameScoreEntity> topGameScoresHard = new ArrayList<>();
        List<GameScoreEntity> topGameScoresMedium = new ArrayList<>();
        List<GameScoreEntity> topGameScoresEasy = new ArrayList<>();
        topGameScoresHard.add(gameScore3);
        topGameScoresHard.add(gameScore4);
        topGameScoresHard.add(gameScore7);
        topGameScoresMedium.add(gameScore1);
        topGameScoresMedium.add(gameScore2);
        topGameScoresEasy.add(gameScore5);
        topGameScoresEasy.add(gameScore6);
        topGameScoresEasy.add(gameScore8);

        // WHEN
        Mockito.when(gameScoreRepositoryMock.findTop100ByGameDifficultyOrderByScoreDesc(HARD)).thenReturn(topGameScoresHard);
        Mockito.when(gameScoreRepositoryMock.findTop100ByGameDifficultyOrderByScoreDesc(MEDIUM)).thenReturn(topGameScoresMedium);
        Mockito.when(gameScoreRepositoryMock.findTop100ByGameDifficultyOrderByScoreDesc(EASY)).thenReturn(topGameScoresEasy);

        // THEN
        GetTopHundreScoresResponse getTopHundreScoresResponse = getTopHundredScoresService.execute();
        Assertions.assertEquals(Long.valueOf(7), getTopHundreScoresResponse.getHard().get(0).getId());
        Assertions.assertEquals(Long.valueOf(4), getTopHundreScoresResponse.getHard().get(getTopHundreScoresResponse.getHard().size() - 1).getId());
        Assertions.assertEquals(Long.valueOf(1), getTopHundreScoresResponse.getMedium().get(0).getId());
        Assertions.assertEquals(Long.valueOf(2), getTopHundreScoresResponse.getMedium().get(getTopHundreScoresResponse.getMedium().size() - 1).getId());
        Assertions.assertEquals(Long.valueOf(6), getTopHundreScoresResponse.getEasy().get(0).getId());
        Assertions.assertEquals(Long.valueOf(5), getTopHundreScoresResponse.getEasy().get(getTopHundreScoresResponse.getEasy().size() - 1).getId());
        Mockito.verify(gameScoreRepositoryMock, times(3)).findTop100ByGameDifficultyOrderByScoreDesc(any());
    }

    @Test
    public void whenGameScoresAreReturnedEmpty(){
        // WHEN
        Mockito.when(gameScoreRepositoryMock.findTop100ByGameDifficultyOrderByScoreDesc(any())).thenReturn(new ArrayList<>());

        // THEN
        GetTopHundreScoresResponse getTopHundreScoresResponse = getTopHundredScoresService.execute();
        Assertions.assertTrue(getTopHundreScoresResponse.getHard().isEmpty());
        Assertions.assertTrue(getTopHundreScoresResponse.getMedium().isEmpty());
        Assertions.assertTrue(getTopHundreScoresResponse.getEasy().isEmpty());
        Mockito.verify(gameScoreRepositoryMock, times(3)).findTop100ByGameDifficultyOrderByScoreDesc(any());
    }
}