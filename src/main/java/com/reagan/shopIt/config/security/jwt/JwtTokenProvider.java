package com.reagan.shopIt.config.security.jwt;

import com.reagan.shopIt.model.exception.JwtAuthenticationException;
import com.reagan.shopIt.util.JwtConfig;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider implements Serializable {

    private final JwtConfig jwtConfig;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenProvider(JwtConfig jwtConfig, UserDetailsService userDetailsService) {
        this.jwtConfig = jwtConfig;
        this.userDetailsService = userDetailsService;
    }

    public String generateJwtToken(String emailAddress) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(emailAddress);
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis() + jwtConfig.getExpiration() * 1000);

//        Map<String, Object> claims = new HashMap<>();
//        claims.put("Username", userDetails.getUsername());
//        claims.put("Roles", userDetails.getAuthorities());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = getUsernameFromToken(token);
            boolean checkToken = isTokenExpired(token);
            return (username.equals(userDetails.getUsername()) && !checkToken);
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("Invalid or expired token", HttpStatus.UNAUTHORIZED);
        }
    }

    public Boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration().before(new Date());
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


}
