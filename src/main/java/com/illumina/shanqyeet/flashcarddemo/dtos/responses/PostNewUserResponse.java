package com.illumina.shanqyeet.flashcarddemo.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostNewUserResponse implements BaseResponse {
    private String username;
    private String userId;
}
