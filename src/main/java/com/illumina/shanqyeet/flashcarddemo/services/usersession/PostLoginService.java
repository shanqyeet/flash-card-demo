package com.illumina.shanqyeet.flashcarddemo.services.usersession;

import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostLoginRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostLoginResponse;
import com.illumina.shanqyeet.flashcarddemo.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.illumina.shanqyeet.flashcarddemo.utils.Constants.TOKEN_PREFIX;

@Slf4j
@Service
public class PostLoginService {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    public PostLoginResponse execute(PostLoginRequest request){
        log.info("#### LOGGING USER: {} #######", request);
        String username = request.getUsername();
        String password = request.getPassword();

        try {

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            log.info("AUTHENTICATION: {]", authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
            return PostLoginResponse.builder()
                    .success(true)
                    .token(jwt)
                    .build();
        } catch (Exception e) {
            log.error("Exception throw: {}", e.getMessage());
            throw e;
        }

    }

}
