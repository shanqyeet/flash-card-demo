package com.illumina.shanqyeet.flashcarddemo.services.mathtablegame;

import com.illumina.shanqyeet.flashcarddemo.dtos.GameScoreCacheObject;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostCompleteGameResponse;
import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import com.illumina.shanqyeet.flashcarddemo.exceptions.GameSessionNotFoundException;
import com.illumina.shanqyeet.flashcarddemo.models.UserEntity;
import com.illumina.shanqyeet.flashcarddemo.repositories.GameScoreRepository;
import com.illumina.shanqyeet.flashcarddemo.services.helpers.mathtablegame.MathTableGameCache;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class PostCompleteGameServiceTest {

    @InjectMocks
    private PostCompleteGameService postCompleteGameServiceMock;

    @Mock
    private MathTableGameCache gameCacheMock;
    @Mock
    private GameScoreRepository gameScoreRepositoryMock;
    @Mock
    private SecurityContext securityContextMock;
    @Mock
    private Authentication authentication;


    UserEntity user = UserEntity.builder()
            .id(UUID.randomUUID())
            .build();

    @BeforeEach
    public void setup(){
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
    }

    @Test
    public void whenSuccessSavingOfResult() throws GameSessionNotFoundException {
        // GIVEN
        GameScoreCacheObject gameScores = GameScoreCacheObject.builder()
                .latestScore(100)
                .latestPenalty(0)
                .totalAnswerTimeInMillis(Long.valueOf(4000000))
                .build();

        // WHEN
        Mockito.when(gameCacheMock.getGameScores(any())).thenReturn(gameScores);
        Mockito.when(gameCacheMock.getGameDifficulty(any())).thenReturn(GameDifficulty.MathTableGame.EASY.name());

        // THEN
        PostCompleteGameResponse response = postCompleteGameServiceMock.execute();
        Assert.notNull(response);
        Assert.notNull(response.getAverageAnswerTime());
        Assert.notNull(response.getCorrectAnswerRate());
        Assert.notNull(response.getTotalScore());
        Assertions.assertEquals(1.0, response.getCorrectAnswerRate());
        Assertions.assertEquals(100, response.getTotalScore());
        Assertions.assertEquals(Long.valueOf(40000), response.getAverageAnswerTime());
        Mockito.verify(gameScoreRepositoryMock, Mockito.times(1)).save(any());
        Mockito.verify(gameCacheMock, Mockito.times(1)).clearCurrentGameData(any());
    }

    @Test
    public void whenNoGameInSessionThrowException() throws GameSessionNotFoundException{
        Exception exception = Assertions.assertThrows(GameSessionNotFoundException.class, () -> {
            postCompleteGameServiceMock.execute();
        });

        String expectedMessage = "There is no on-going game found, please start new game";
        String exceptionMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, exceptionMessage);
    }


}