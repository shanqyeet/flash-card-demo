package com.illumina.shanqyeet.flashcarddemo.dtos.requests;

import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import com.illumina.shanqyeet.flashcarddemo.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetNextChallengeRequest implements BaseRequest {
    private String userId;
    private GameStatus.MathTableGame gameStatus;
    private GameDifficulty.MathTableGame gameDifficulty;
}
