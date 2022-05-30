package com.illumina.shanqyeet.flashcarddemo.dtos.responses;

import com.illumina.shanqyeet.flashcarddemo.models.GameScoreEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTopHundreScoresResponse {
    private List<GameScoreEntity> top100Scores;
}
