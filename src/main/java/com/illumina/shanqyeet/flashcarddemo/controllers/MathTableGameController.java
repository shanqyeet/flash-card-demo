package com.illumina.shanqyeet.flashcarddemo.controllers;

import com.illumina.shanqyeet.flashcarddemo.dtos.ChallengeResultDto;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.GetNextChallengeRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostChallengeResultRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetNextChallengeResponse;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetValidateOnGoingGameResponse;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostChallengeResultResponse;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostCompleteGameResponse;
import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import com.illumina.shanqyeet.flashcarddemo.enums.GameStatus;
import com.illumina.shanqyeet.flashcarddemo.services.mathtablegame.GetNextChallengeService;
import com.illumina.shanqyeet.flashcarddemo.services.mathtablegame.GetValidateOnGoingGameService;
import com.illumina.shanqyeet.flashcarddemo.services.mathtablegame.PostChallengeResultService;
import com.illumina.shanqyeet.flashcarddemo.services.mathtablegame.PostCompleteGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/math")
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
    ){
        return getValidateOnGoingGameService.execute();
    }

    @GetMapping("/challenge/new")
    public GetNextChallengeResponse retrieveNextChallenge(
            @PathVariable String userId,
            @RequestParam(name = "game-status", required = false) String gameStatus,
            @RequestParam(name = "game-difficulty", required = false) String gameDifficulty) throws Exception {


        GetNextChallengeRequest request = GetNextChallengeRequest.builder()
                .userId(userId)
                .gameDifficulty(GameDifficulty.MathTableGame.fromString(gameDifficulty))
                .gameStatus(GameStatus.MathTableGame.fromString(gameStatus))
                .build();
        return getNextChallengeService.execute(request);
    }

    @PostMapping("/challenge/result")
    public PostChallengeResultResponse updateChallengeResult(
            @RequestBody ChallengeResultDto challengeResult
    ) throws Exception {
        PostChallengeResultRequest request = PostChallengeResultRequest.builder()
                .isPassed(challengeResult.isChallengePassed())
                .answerTimeInMillis(challengeResult.getAnswerTimeInMillis())
                .build();
        return postChallengeResultService.execute(request);
    }

    @PostMapping("/challenge/complete")
    public PostCompleteGameResponse completeChallenge(
            @RequestBody ChallengeResultDto challengeResult
    ){
        return postCompleteGameService.execute();
    }
}
