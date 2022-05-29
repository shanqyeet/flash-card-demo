package com.illumina.shanqyeet.flashcarddemo.controllers;

import com.illumina.shanqyeet.flashcarddemo.payload.JWTLoginSuccessResponse;
import com.illumina.shanqyeet.flashcarddemo.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.illumina.shanqyeet.flashcarddemo.utils.Constants.GameStatus.TOKEN_PREFIX;

@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RestController
@RequestMapping("/users/sessions")
public class UserSessionsController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @PostMapping("/new")
    public void login(){
        //authenticaton manager .authenticate
//        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password) <-- from request

//        SecurityContextHolder.getContext().setAuthentication(authentication);
          String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
          return response entity(new JWTLoginSuccessResponse(true, jwt));

    }
}
