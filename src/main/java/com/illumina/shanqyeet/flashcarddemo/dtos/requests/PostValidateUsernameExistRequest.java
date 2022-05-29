package com.illumina.shanqyeet.flashcarddemo.dtos.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostValidateUsernameExistRequest implements BaseRequest{
    private String username;
}
