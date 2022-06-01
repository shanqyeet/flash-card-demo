package com.illumina.shanqyeet.flashcarddemo.services.mathtablegame;


import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostChallengeRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostChallengeResponse;
import com.illumina.shanqyeet.flashcarddemo.exceptions.GameSessionNotFoundException;
import com.illumina.shanqyeet.flashcarddemo.models.UserEntity;
import com.illumina.shanqyeet.flashcarddemo.services.helpers.mathtablegame.MathTableGameCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.OptionalInt;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import static com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty.MathTableGame.EASY;
import static com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty.MathTableGame.HARD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PostChallengeServiceTest {

    @InjectMocks
    private PostChallengeService getChallengeServiceMock;
    @Mock
    private MathTableGameCache gameCacheMock;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Random randomNumGenerator;
    @Mock
    private IntStream randomIntStream;
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

    public void mockSetupForNonExceptionCase(){
        OptionalInt optionalInteger1 = OptionalInt.of(5);
        OptionalInt optionalInteger2 = OptionalInt.of(3);
        Mockito.when(randomNumGenerator.ints(anyInt(), anyInt())).thenReturn(randomIntStream);
        Mockito.when(randomIntStream.findFirst()).thenReturn(optionalInteger1).thenReturn(optionalInteger2);
    }

    @Test
    public void whenSuccessGettingNewChallenge() throws Exception {
        //GIVEN
        mockSetupForNonExceptionCase();
        PostChallengeRequest request = PostChallengeRequest.builder()
                .gameDifficulty(EASY)
                .isNewGame(true)
                .build();

        //THEN
        PostChallengeResponse response = getChallengeServiceMock.execute(request);
        Assertions.assertNotNull(response.getFirstNum());
        Assertions.assertNotNull(response.getSecondNum());
        Assertions.assertNotNull(response.getResult());
        Assertions.assertNotNull(response.getOperator());
        Assertions.assertEquals(EASY.name(), response.getGameDifficult());
        Mockito.verify(gameCacheMock, times(1)).clearCurrentGameData(any());
        Mockito.verify(gameCacheMock, times(1)).putGameScores(any(), any());
        Mockito.verify(gameCacheMock, times(1)).putGameDifficulty(any(), any());
        Mockito.verify(gameCacheMock, times(1)).putNumPairFrequency(any(), any());
        Mockito.verify(gameCacheMock, times(0)).getGameDifficulty(any());
    }


    @Test
    public void whenSuccessGettingOnGoingChallenge() throws Exception {
        //GIVEN
        mockSetupForNonExceptionCase();
        Mockito.when(gameCacheMock.getGameDifficulty(any())).thenReturn(EASY.name());
        PostChallengeRequest request = PostChallengeRequest.builder()
                .isNewGame(false)
                .build();

        //THEN
        PostChallengeResponse response = getChallengeServiceMock.execute(request);
        Assertions.assertNotNull(response.getFirstNum());
        Assertions.assertNotNull(response.getSecondNum());
        Assertions.assertNotNull(response.getResult());
        Assertions.assertNotNull(response.getOperator());
        Assertions.assertEquals(EASY.name(), response.getGameDifficult());
        Mockito.verify(gameCacheMock, times(0)).clearCurrentGameData(any());
        Mockito.verify(gameCacheMock, times(0)).putGameScores(any(), any());
        Mockito.verify(gameCacheMock, times(0)).putGameDifficulty(any(), any());
        Mockito.verify(gameCacheMock, times(1)).putNumPairFrequency(any(), any());
        Mockito.verify(gameCacheMock, times(1)).getGameDifficulty(any());
    }

    @Test
    public void whenSuccessGettingOnGoingChallengeWithCorrectDifficultWithWrongDifficultyLevelSentByClient() throws Exception {
        //GIVEN
        mockSetupForNonExceptionCase();
        Mockito.when(gameCacheMock.getGameDifficulty(any())).thenReturn(EASY.name());
        PostChallengeRequest request = PostChallengeRequest.builder()
                .isNewGame(false)
                .gameDifficulty(HARD)
                .build();

        //THEN
        PostChallengeResponse response = getChallengeServiceMock.execute(request);
        Assertions.assertNotNull(response.getFirstNum());
        Assertions.assertNotNull(response.getSecondNum());
        Assertions.assertNotNull(response.getResult());
        Assertions.assertNotNull(response.getOperator());
        Assertions.assertEquals(EASY.name(), response.getGameDifficult());
        Mockito.verify(gameCacheMock, times(0)).clearCurrentGameData(any());
        Mockito.verify(gameCacheMock, times(0)).putGameScores(any(), any());
        Mockito.verify(gameCacheMock, times(0)).putGameDifficulty(any(), any());
        Mockito.verify(gameCacheMock, times(1)).putNumPairFrequency(any(), any());
        Mockito.verify(gameCacheMock, times(1)).getGameDifficulty(any());
    }


    @Test
    public void whenOnGoingGameFoundNoGameDifficultyRecord() {
        //GIVEN
        PostChallengeRequest request = PostChallengeRequest.builder()
                .isNewGame(false)
                .build();
        Exception exception = Assertions.assertThrows(GameSessionNotFoundException.class, () -> {
            getChallengeServiceMock.execute(request);
        });

        String expectedMessage = "There is no on-going game found, please start new game";
        String exceptionMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, exceptionMessage);       //THEN
    }


}