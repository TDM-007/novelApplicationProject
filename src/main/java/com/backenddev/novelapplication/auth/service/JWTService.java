package com.backenddev.novelapplication.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {


    private String secretKey;

    //key generator.
    public JWTService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("HmacSHA256 algorithm not found", e);
        }
    }



    // generate jwt token.
    public String generateToken(String userName) {

        Map<String, Object> claims = new HashMap<String, Object>();// extra claims , put("role", "User")

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt( new Date(System.currentTimeMillis())) //sets the issued date.
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))// 30 minutes token expiry time
                .and()
                .signWith(getKey())
                .compact();

    }

    //get the generated key

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String jwtToken) {
        return extractAllClaims(jwtToken).getSubject();
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

   public boolean isTheTokenValid(String jwtToken, UserDetails userDetails) {
        final String userName = extractUserName(jwtToken);
        return (userName.equals(userDetails.getUsername()));
   }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractAllClaims(jwtToken).getExpiration();
    }
}
