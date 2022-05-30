package com.illumina.shanqyeet.flashcarddemo.security;

import com.illumina.shanqyeet.flashcarddemo.models.UserEntity;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.illumina.shanqyeet.flashcarddemo.utils.Constants.SECRET;
import static com.illumina.shanqyeet.flashcarddemo.utils.Constants.TOKEN_EXPIRY_TIME;


@Slf4j
@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication){
        log.info("GENERATING TOKEN");
        log.info("AUTH: {}", authentication);

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

    //Validate the token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException ex){
            System.out.println("Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            System.out.println("Expired JWT token");
        }catch (UnsupportedJwtException ex){
            System.out.println("Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            System.out.println("JWT claims string is empty");
        }
        return false;
    }


    //Get user Id from token

    public String getUserIdFromJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String)claims.get("id");
        return id;
    }
}
