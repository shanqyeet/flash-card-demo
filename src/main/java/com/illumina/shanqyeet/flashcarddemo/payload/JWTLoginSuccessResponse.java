package com.illumina.shanqyeet.flashcarddemo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JWTLoginSuccessResponse {
    private boolean success;
    private String token;
}
