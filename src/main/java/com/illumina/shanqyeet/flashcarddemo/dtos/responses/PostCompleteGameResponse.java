package com.illumina.shanqyeet.flashcarddemo.dtos.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCompleteGameResponse implements BaseResponse{
    private Integer totalScore;
    private Float correctAnswerRate;
    private Long averageAnswerTime;
}
