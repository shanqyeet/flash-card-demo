package com.illumina.shanqyeet.flashcarddemo.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvalidLoginResponse {
    private String username;
    private String password;

    public InvalidLoginResponse(){
        this.username = "invalid username";
        this.password = "invalid password";
    }
}
