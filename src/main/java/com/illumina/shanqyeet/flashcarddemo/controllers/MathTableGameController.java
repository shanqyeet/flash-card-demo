package com.illumina.shanqyeet.flashcarddemo.controllers;

import com.illumina.shanqyeet.flashcarddemo.dtos.ChallengeResultDto;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostChallengeRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostChallengeResultRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostChallengeResponse;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetValidateOnGoingGameResponse;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostChallengeResultResponse;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostCompleteGameResponse;
import com.illumina.shanqyeet.flashcarddemo.exceptions.GameSessionNotFoundException;
import com.illumina.shanqyeet.flashcarddemo.services.mathtablegame.PostChallengeService;
import com.illumina.shanqyeet.flashcarddemo.services.mathtablegame.GetValidateOnGoingGameService;
import com.illumina.shanqyeet.flashcarddemo.services.mathtablegame.PostChallengeResultService;
import com.illumina.shanqyeet.flashcarddemo.services.mathtablegame.PostCompleteGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/math")
public class MathTableGameController {

    @Autowired
    private GetValidateOnGoingGameService getValidateOnGoingGameService;
    @Autowired
    private PostChallengeService getNextChallengeService;
    @Autowired
    private PostChallengeResultService postChallengeResultService;
    @Autowired
    private PostCompleteGameService postCompleteGameService;

    @GetMapping("/challenge/check-session")
    public GetValidateOnGoingGameResponse validateOnGoingGame(
    ){
        return getValidateOnGoingGameService.execute();
    }

    @PostMapping("/challenge/new")
    public PostChallengeResponse retrieveChallenge(@Valid @RequestBody PostChallengeRequest request) throws Exception {
        return getNextChallengeService.execute(request);
    }

    @PostMapping("/challenge/result")
    public PostChallengeResultResponse updateChallengeResult(
            @RequestBody ChallengeResultDto challengeResult
    ) throws Exception {
        log.info("CHALLENGE RESULT");
        log.info(challengeResult.toString());
        PostChallengeResultRequest request = PostChallengeResultRequest.builder()
                .isPassed(challengeResult.isChallengePassed())
                .answerTimeInMillis(challengeResult.getAnswerTimeInMillis())
                .build();
        return postChallengeResultService.execute(request);
    }

    @PutMapping("/challenge/complete")
    public PostCompleteGameResponse completeChallenge(
            @RequestBody ChallengeResultDto challengeResult
    ) throws GameSessionNotFoundException {
        return postCompleteGameService.execute();
    }
}
