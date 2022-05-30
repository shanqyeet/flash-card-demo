package com.illumina.shanqyeet.flashcarddemo.services.helpers.users;

import com.illumina.shanqyeet.flashcarddemo.models.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtUserDetailsExtractor {
    public static UserEntity getUserFromContext(){
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
