package com.illumina.shanqyeet.flashcarddemo.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetValidateOnGoingGameResponse implements BaseResponse {
    private boolean hasOnGoingGame;
}
