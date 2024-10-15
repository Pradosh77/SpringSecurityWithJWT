package com.example.springSecurityWebWithJWT.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private final String secretKey;

    private static final long JWT_TOKEN_VALIDITY =  5 * 60 * 60 ; // 5 hours

    // Constructor to generate the secret key
    public JwtTokenUtil() {
        this.secretKey = generateSecretKey();
    }

    // Generate a secret key using HmacSHA512 and encode it as Base64
    private String generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
            keyGen.init(512); // Generate a 512-bit key for HmacSHA512
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("Error generating secret key", e);
        }
    }

    // Retrieve username from JWT token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Retrieve expiration date from JWT token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Retrieve claims using the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(Base64.getDecoder().decode(secretKey)).build().parseClaimsJws(token).getBody();
    }

    // Check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Generate token for user
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)) // 5 hours validity
                .signWith(SignatureAlgorithm.HS512, Base64.getDecoder().decode(secretKey)) // Use HS512 with secret key
                .compact();
    }

    // Validate the token
    public Boolean validateToken(String token, String username) {
        final String usernameFromToken = getUsernameFromToken(token);
        return (usernameFromToken.equals(username) && !isTokenExpired(token));
    }
}
