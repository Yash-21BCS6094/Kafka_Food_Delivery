package com.example.api_gateway.util;

import com.example.api_gateway.exception.InvalidAccessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    public void validateToken(final String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            log.info("JWT validation successful");
        } catch (Exception e) {
            log.error("JWT validation failed: {}", e.getMessage());
            throw new InvalidAccessException("Access to protected resource | Provide valid Jwt Token");
        }
    }

    /**
     * Extracts claims from the JWT token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extracts the username (subject) from the JWT token.
     */
    public String extractUsernameFromToken(String token) {
        return extractAllClaims(token).getSubject(); // "sub" claim typically stores the username
    }

    /**
     * Extracts the role from the JWT token.
     */
    public String extractRoleFromToken(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
