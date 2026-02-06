package org.amalitech.springsecuritydemo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final static String JWT_SECRET = "TIiPDtrODkZq8fAHwcFOL5yxdHsAASbKuDLYcwVHC5V5iy95V8Pf05LnWNricbehUJHwE2B8zaKTFF0p8O30og";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    public String generateToken(Authentication authentication) {
        String name = authentication.getName();
        var roles = authentication.getAuthorities();

        Instant iss = Instant.now();
        Instant exp = iss.plus(24, ChronoUnit.HOURS);

        return Jwts.builder()
                .subject(name)
                .claim("roles", Arrays.toString(roles.toArray()))
                .issuedAt(Date.from(iss))
                .expiration(Date.from(exp))
                .id(String.valueOf(UUID.randomUUID()))
                .signWith(KEY)
                .compact();

    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token) {
        try {
            return parseClaims(token).getExpiration().after(Date.from(Instant.now()));
        } catch (JwtException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public String extractUsernameFromTokenHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return token.isBlank() ? null : extractUsername(token);
        }
        return null;
    }

}