package com.illumina.shanqyeet.flashcarddemo.services.helpers.users;

import com.illumina.shanqyeet.flashcarddemo.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UserDetailsValidator implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("user not found");
        }
    }

    public UserDetails loadUserById(String userId) throws UsernameNotFoundException {
        try {
            return userRepository.findById(UUID.fromString(userId));
        } catch (Exception e) {
            throw new UsernameNotFoundException("user not found");
        }
    }
}
