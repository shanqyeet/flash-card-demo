package com.illumina.shanqyeet.flashcarddemo.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetValidateOnGoingGameResponse implements BaseResponse {
    @JsonProperty(value = "hasOngGoingGame")
    private boolean hasOnGoingGame;
}
