package com.illumina.shanqyeet.flashcarddemo.controllers;

import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostLoginRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostLoginResponse;
import com.illumina.shanqyeet.flashcarddemo.services.usersession.PostLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RestController
@RequestMapping("/users/sessions")
public class UserSessionsController {

    @Autowired
    private PostLoginService postLoginService;

    @PostMapping("/new")
    public PostLoginResponse login(@RequestBody PostLoginRequest request){
        return postLoginService.execute(request);
    }
}
