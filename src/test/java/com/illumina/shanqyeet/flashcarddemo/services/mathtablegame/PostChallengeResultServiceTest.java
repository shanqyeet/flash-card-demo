package com.illumina.shanqyeet.flashcarddemo.services.mathtablegame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.illumina.shanqyeet.flashcarddemo.dtos.GameScoreCacheDto;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostChallengeResultRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostChallengeResultResponse;
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
class PostChallengeResultServiceTest {

    @InjectMocks
    private PostChallengeResultService postChallengeResultServiceMock;

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
    public void whenChallengIsPassedFirsTime() throws JsonProcessingException {
        //GIVEN
        PostChallengeResultRequest request = PostChallengeResultRequest.builder()
                .isPassed(true)
                .answerTimeInMillis(Long.valueOf(10000))
                .build();

        //THEN
        PostChallengeResultResponse response = postChallengeResultServiceMock.execute(request);
        Assertions.assertEquals(1, response.getTotalScore());
        Assertions.assertEquals(0, response.getTotalPenalty());
        Mockito.verify(gameCacheMock, Mockito.times(1)).getGameScores(any());
        Mockito.verify(gameCacheMock, Mockito.times(1)).putGameScores(any(), any());
    }

    @Test
    public void whenChallengeIsNotPassedFirstTime() throws JsonProcessingException {
        //GIVEN
        PostChallengeResultRequest request = PostChallengeResultRequest.builder()
                .isPassed(false)
                .answerTimeInMillis(Long.valueOf(10000))
                .build();

        //THEN
        PostChallengeResultResponse response = postChallengeResultServiceMock.execute(request);
        Assertions.assertEquals(0, response.getTotalScore());
        Assertions.assertEquals(1, response.getTotalPenalty());
        Mockito.verify(gameCacheMock, Mockito.times(1)).getGameScores(any());
        Mockito.verify(gameCacheMock, Mockito.times(1)).putGameScores(any(), any());
    }

    @Test
    public void whenChallengIsPassedWithExistingRecords() throws JsonProcessingException {
        //GIVEN
        PostChallengeResultRequest request = PostChallengeResultRequest.builder()
                .isPassed(true)
                .answerTimeInMillis(Long.valueOf(10000))
                .build();

        GameScoreCacheDto gameScore = GameScoreCacheDto.builder()
                .latestScore(10)
                .latestPenalty(0)
                .totalAnswerTimeInMillis(Long.valueOf(50000))
                .build();
        //WHEN
        Mockito.when(gameCacheMock.getGameScores(any())).thenReturn(gameScore);

        //THEN
        PostChallengeResultResponse response = postChallengeResultServiceMock.execute(request);
        Assertions.assertEquals(11, response.getTotalScore());
        Assertions.assertEquals(0, response.getTotalPenalty());
        Mockito.verify(gameCacheMock, Mockito.times(1)).getGameScores(any());
        Mockito.verify(gameCacheMock, Mockito.times(1)).putGameScores(any(), any());
    }

}