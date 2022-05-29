package com.illumina.shanqyeet.flashcarddemo.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
