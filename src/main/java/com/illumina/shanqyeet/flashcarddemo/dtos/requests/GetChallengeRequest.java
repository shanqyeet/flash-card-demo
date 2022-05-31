package com.illumina.shanqyeet.flashcarddemo.dtos.requests;

import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetChallengeRequest implements BaseRequest {
    @NonNull
    private boolean isNewGame;
    private GameDifficulty.MathTableGame gameDifficulty;
}
