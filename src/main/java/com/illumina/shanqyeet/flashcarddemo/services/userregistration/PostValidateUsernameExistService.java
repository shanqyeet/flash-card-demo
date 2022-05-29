package com.illumina.shanqyeet.flashcarddemo.services.userregistration;

import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostValidateUsernameExistRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostValidateUsernameExistResponse;
import com.illumina.shanqyeet.flashcarddemo.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostValidateUsernameExistService {

    @Autowired
    private UserRepository userRepository;

    public PostValidateUsernameExistResponse execute(PostValidateUsernameExistRequest request){
        boolean isExist = userRepository.existsByUsername(request.getUsername());
        return PostValidateUsernameExistResponse.builder()
                .username(request.getUsername())
                .isExist(isExist)
                .build();
    }
}
