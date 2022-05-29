package com.illumina.shanqyeet.flashcarddemo.controllers;

import com.illumina.shanqyeet.flashcarddemo.dtos.ChallengeResultDto;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.GetNextChallengeRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.GetValidateOnGoingGameRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostChallengeResultRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostCompleteGameRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetNextChallengeResponse;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetValidateOnGoingGameResponse;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostChallengeResultResponse;
import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import com.illumina.shanqyeet.flashcarddemo.enums.GameStatus;
import com.illumina.shanqyeet.flashcarddemo.services.mathtablegame.GetNextChallengeService;
import com.illumina.shanqyeet.flashcarddemo.services.mathtablegame.GetValidateOnGoingGameService;
import com.illumina.shanqyeet.flashcarddemo.services.mathtablegame.PostChallengeResultService;
import com.illumina.shanqyeet.flashcarddemo.services.mathtablegame.PostCompleteGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users/{userId}/math")
public class MathTableGameController {

    @Autowired
    private GetValidateOnGoingGameService getValidateOnGoingGameService;
    @Autowired
    private GetNextChallengeService getNextChallengeService;
    @Autowired
    private PostChallengeResultService postChallengeResultService;
    @Autowired
    private PostCompleteGameService postCompleteGameService;

    @GetMapping("/challenge/check-session")
    public GetValidateOnGoingGameResponse retrieveNextChallenge(
            @PathVariable String userId
    ){
        GetValidateOnGoingGameRequest request = GetValidateOnGoingGameRequest.builder()
                .userId(userId)
                .build();
        return getValidateOnGoingGameService.execute(request);
    }

    @GetMapping("/challenge/new")
    public GetNextChallengeResponse retrieveNextChallenge(
            @PathVariable String userId,
            @RequestParam(name = "game-status", required = false) String gameStatus,
            @RequestParam(name = "game-difficulty", required = false) String gameDifficulty){
        log.info("############# IN CONTROLLER ##################");
        log.info("GAME STATUS: ", gameStatus);
        log.info("GAME DIFFICULTY: ", gameDifficulty);
        log.info("GAME STATUS PARSED: ", GameDifficulty.MultiplicationTableGame.fromString(gameDifficulty));
        log.info("GAME DIFFICULTY PARSED: ", GameStatus.MultiplicationTableGame.fromString(gameStatus));


        GetNextChallengeRequest request = GetNextChallengeRequest.builder()
                .userId(userId)
                .gameDifficulty(GameDifficulty.MultiplicationTableGame.fromString(gameDifficulty))
                .gameStatus(GameStatus.MultiplicationTableGame.fromString(gameStatus))
                .build();
        return getNextChallengeService.execute(request);
    }

    @PostMapping("/challenge/result")
    public PostChallengeResultResponse updateChallengeResult(
            @PathVariable String userId,
            @RequestBody ChallengeResultDto challengeResult
    ){
        PostChallengeResultRequest request = PostChallengeResultRequest.builder()
                .userId(userId)
                .isPassed(challengeResult.isChallengePassed())
                .answerTimeInMillis(challengeResult.getAnswerTimeInMillis())
                .build();
        return postChallengeResultService.execute(request);
    }

    @PostMapping("/challenge/complete")
    public ResponseEntity<String> completeChallenge(
            @PathVariable String userId,
            @RequestBody ChallengeResultDto challengeResult
    ){
        PostCompleteGameRequest request = PostCompleteGameRequest.builder()
                .userId(userId)
                .build();
        postCompleteGameService.execute(request);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }
}
