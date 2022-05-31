package com.illumina.shanqyeet.flashcarddemo.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetChallengeRequest implements BaseRequest {
    @NotNull
    @JsonProperty("isNewGame")
    private Boolean isNewGame;
    private GameDifficulty.MathTableGame gameDifficulty;
}
