package com.illumina.shanqyeet.flashcarddemo.controllers;

import com.illumina.shanqyeet.flashcarddemo.dtos.responses.GetTopHundreScoresResponse;
import com.illumina.shanqyeet.flashcarddemo.services.gamescorehistory.GetTopHundredScoresService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/scores")
public class GameScoreController {

    @Autowired
    private GetTopHundredScoresService getTopHundredScoresService;

    @GetMapping("/top-100-leaders")
    public GetTopHundreScoresResponse getTopHundredScores(){
        return getTopHundredScoresService.execute();
    }
}
