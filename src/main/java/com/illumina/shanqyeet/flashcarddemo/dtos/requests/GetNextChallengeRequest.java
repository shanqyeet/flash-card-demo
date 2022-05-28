package com.illumina.shanqyeet.flashcarddemo.dtos.requests;

import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import com.illumina.shanqyeet.flashcarddemo.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetNextChallengeRequest implements BaseRequest {
    private UUID userId;
    private GameStatus.MultiplicationTableGame gameStatus;
    private GameDifficulty.MultiplicationTableGame gameDifficulty;
}
