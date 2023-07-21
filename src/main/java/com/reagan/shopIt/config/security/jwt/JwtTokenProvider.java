package com.reagan.shopIt.config.security.jwt;

import com.reagan.shopIt.model.exception.JwtAuthenticationException;
import com.reagan.shopIt.util.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
@Component
public class JwtTokenProvider implements Serializable {

    private final JwtConfig jwtConfig;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenProvider(JwtConfig jwtConfig, UserDetailsService userDetailsService) {
        this.jwtConfig = jwtConfig;
        this.userDetailsService = userDetailsService;
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwtToken(String emailAddress) {
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis() + jwtConfig.getExpiration() * 1000);
        return Jwts.builder()
                .setSubject(emailAddress)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key())
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            boolean checkToken = isTokenExpired(token);
            String username = getUsernameFromToken(token);

            return (username.equals(userDetails.getUsername()) && !checkToken);
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("Invalid or expired token", HttpStatus.UNAUTHORIZED);
        }
    }

    public Boolean isTokenExpired(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration().before(new Date());
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
