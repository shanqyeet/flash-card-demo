package com.illumina.shanqyeet.flashcarddemo.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChallengeResultDto implements Serializable {
    private boolean isChallengePassed;
    private Long answerTimeInMillis;

}
