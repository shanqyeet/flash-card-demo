package com.illumina.shanqyeet.flashcarddemo.services.userregistration;


import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostNewUserRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.responses.PostNewUserResponse;
import com.illumina.shanqyeet.flashcarddemo.models.UserEntity;
import com.illumina.shanqyeet.flashcarddemo.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostNewUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public PostNewUserResponse execute(PostNewUserRequest request) throws Exception {
        if(newPasswordsUnmatched(request.getPassword(), request.getConfirmPassword())){
            throw new Exception("Password and Confirm Password Unmatched");
        }

        UserEntity newUser = UserEntity.builder()
                .username(request.getUsername())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .avatarUrl(request.getAvatarUrl())
                .build();

        UserEntity savedUser = userRepository.save(newUser);
        return PostNewUserResponse.builder()
                .userId(savedUser.getId().toString())
                .username(savedUser.getUsername())
                .build();
    }

    private boolean newPasswordsUnmatched(String password, String confirmPasswrod){
        return !password.equals(confirmPasswrod);
    }
}
