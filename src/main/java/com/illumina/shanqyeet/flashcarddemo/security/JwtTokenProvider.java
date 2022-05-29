package com.illumina.shanqyeet.flashcarddemo.security;

import com.illumina.shanqyeet.flashcarddemo.models.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.illumina.shanqyeet.flashcarddemo.utils.Constants.GameStatus.SECRET;
import static com.illumina.shanqyeet.flashcarddemo.utils.Constants.GameStatus.TOKEN_EXPIRY_TIME;

@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        String userId = user.getId().toString();
        Date now = new Date(System.currentTimeMillis());
        Date expiry = new Date(now.getTime() + TOKEN_EXPIRY_TIME);
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId);
        claims.put("username", user.getUsername());
        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}
