package com.pm.authservice.Util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key secretKey;

    // instead of storing the key into the code
    // storing secret key as an environment variable
    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        // String to byte array
        byte[] keyByte = Base64.getDecoder().decode(secretKey.getBytes(
                StandardCharsets.UTF_8
        ));

        this.secretKey = Keys.hmacShaKeyFor(keyByte);
    }

    public String generateToken(String email, String role){
        // Jwts will create an Object of jwt token
        // it is of type String

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(secretKey)
                .compact();
        // compact will give all the things into a string
    }

    public void validateToken(String token){
        try{
            Jwts.parser().verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token);
        }catch (SignatureException e){
            throw new JwtException("Invalid Jwt signature");
        }catch (JwtException e){
            throw new JwtException("Invalid Jwt");
        }
    }
}
