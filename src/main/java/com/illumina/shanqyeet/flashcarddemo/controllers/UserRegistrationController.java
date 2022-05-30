package com.illumina.shanqyeet.flashcarddemo.controllers;

import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostNewUserRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostValidateUsernameExistRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostNewUserResponse;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostValidateUsernameExistResponse;
import com.illumina.shanqyeet.flashcarddemo.services.userregistration.PostNewUserService;
import com.illumina.shanqyeet.flashcarddemo.services.userregistration.PostValidateUsernameExistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RestController
@RequestMapping("/users/registrations")
public class UserRegistrationController {

    @Autowired
    private PostValidateUsernameExistService postValidateUsernameExistService;

    @Autowired
    private PostNewUserService postNewUserService;

    @PostMapping("/validate-username")
    public PostValidateUsernameExistResponse validateUsername(@RequestBody PostValidateUsernameExistRequest request){
        return postValidateUsernameExistService.execute(request);
    }

    @PostMapping("/new")
    public PostNewUserResponse registerNewUser(@RequestBody PostNewUserRequest request) throws Exception {
        return postNewUserService.execute(request);
    }

}
