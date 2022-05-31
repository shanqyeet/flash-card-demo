package com.illumina.shanqyeet.flashcarddemo.services.mathtablegame;

import com.illumina.shanqyeet.flashcarddemo.dtos.GameScoreCacheObject;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetValidateOnGoingGameResponse;
import com.illumina.shanqyeet.flashcarddemo.models.UserEntity;
import com.illumina.shanqyeet.flashcarddemo.services.helpers.mathtablegame.MathTableGameCache;
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
class GetValidateOnGoingGameServiceTest {

    @InjectMocks
    private GetValidateOnGoingGameService getValidateOnGoingGameServiceMock;

    @Mock
    private MathTableGameCache gameCacheMock;
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
    public void whenThereIsOnGoingGameSession() {
        GameScoreCacheObject gameScore = GameScoreCacheObject.builder()
                .latestScore(1)
                .totalAnswerTimeInMillis(Long.valueOf(20000))
                .latestPenalty(0)
                .build();
        Mockito.when(gameCacheMock.getGameScores(any())).thenReturn(gameScore);

        GetValidateOnGoingGameResponse response = getValidateOnGoingGameServiceMock.execute();
        Assertions.assertEquals(Boolean.TRUE, response.isHasOnGoingGame());
    }

    @Test
    public void whenThereIsNoOnGoingGameSessionWithNonNullGameScoreContainingZeroScores(){
        GameScoreCacheObject gameScore = GameScoreCacheObject.builder()
                .latestScore(0)
                .totalAnswerTimeInMillis(Long.valueOf(20000))
                .latestPenalty(0)
                .build();
        Mockito.when(gameCacheMock.getGameScores(any())).thenReturn(gameScore);

        GetValidateOnGoingGameResponse response = getValidateOnGoingGameServiceMock.execute();
        Assertions.assertEquals(Boolean.FALSE, response.isHasOnGoingGame());

    }

    @Test
    public void whenThereIsNoOnGoingGameSessionWithNullGameScore(){
        GetValidateOnGoingGameResponse response = getValidateOnGoingGameServiceMock.execute();
        Assertions.assertEquals(Boolean.FALSE, response.isHasOnGoingGame());
    }
}