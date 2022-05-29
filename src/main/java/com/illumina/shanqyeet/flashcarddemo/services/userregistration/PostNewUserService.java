package com.illumina.shanqyeet.flashcarddemo.services.userregistration;


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
    public void execute(){
//        UserEntity newUser = UserEntity.builder()
//                .username()
//                .password(bCryptPasswordEncoder.encode("password"))
//                .avatarUrl()
//                .build();
//
//        userRepository.save(newUser);

    }
}
