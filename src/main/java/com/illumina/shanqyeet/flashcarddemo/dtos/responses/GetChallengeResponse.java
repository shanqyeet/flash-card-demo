package com.illumina.shanqyeet.flashcarddemo.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetChallengeResponse implements BaseResponse {

    @Min(value = 0)
    @Max(value = 12)
    private Integer firstNum;

    @Min(value = 0)
    @Max(value = 12)
    private Integer secondNum;
    private Integer result;
    private Character operator;
    private String gameDifficult;
}
