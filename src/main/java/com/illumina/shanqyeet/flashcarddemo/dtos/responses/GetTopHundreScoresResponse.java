package com.illumina.shanqyeet.flashcarddemo.dtos.responses;

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
    private List<TopScoresDto> hard;
    private List<TopScoresDto> medium;
    private List<TopScoresDto> easy;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopScoresDto {
        private long id;
        private String username;
        private Integer score;
        private Integer penalty;
        private Long averageAnswerTimeInMillis;
    }
}
