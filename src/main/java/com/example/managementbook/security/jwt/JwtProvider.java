package com.example.managementbook.security.jwt;

import com.example.managementbook.security.UserPrinciple;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;


@Component
public class JwtProvider {
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;
    @Value("${EXPIRED}")
    private Long EXPIRED;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String generateToken(UserPrinciple  userPrinciple) {
        Date dateExpiration = new Date(new Date().getTime() + EXPIRED);

    return Jwts.builder()
            .setSubject(userPrinciple.getUsername())
            .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
            .setExpiration(dateExpiration)
            .compact();
    }
    public Boolean validateToken(String token) {
        try{
        Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);

        return true;
        }catch(ExpressionException | ExpiredJwtException| MalformedJwtException exception){
            logger.error("Can't validate token ",exception.getMessage());
        }
        return false;
    }
    public String getUserNameFromToken(String token) {
        return  Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
