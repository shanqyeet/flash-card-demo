package com.illumina.shanqyeet.flashcarddemo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameScoreCacheObject implements Serializable {
    private Integer latestScore = 0;
    private Integer latestPenalty = 0;
    private Long totalAnswerTimeInMillis = Long.valueOf(0);
}
