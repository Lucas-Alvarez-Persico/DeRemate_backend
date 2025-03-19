package com.deremate.demo.Config;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class JwtService {
    @Value("${application.security.jwt.secretKey}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.recoverExpiration}")
    private long recoverExpiration;

    public String generateAuthToken(UserDetails userDetails) {
        return buildToken(userDetails, jwtExpiration, "AUTH");
    }

    public String generateRecoverToken(UserDetails userDetails) {
        return buildToken(userDetails, recoverExpiration, "RECOVER");
    }

    private String buildToken(UserDetails userDetails, long expiration, String purpose) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .claim("purpose", purpose)
                .signWith(getSecretKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails, String expectedPurpose) {
        final String username = extractClaim(token, Claims::getSubject);
        final String tokenType = extractClaim(token, claims -> claims.get("purpose", String.class));

        return username.equals(userDetails.getUsername()) &&
                !isTokenExpired(token) &&
                expectedPurpose.equals(tokenType);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    
}