package com.illumina.shanqyeet.flashcarddemo.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostNewUserRequest implements BaseRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String avatarUrl;
}
