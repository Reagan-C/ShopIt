package com.reagan.shopIt.model.security;

import com.reagan.shopIt.model.exception.JwtAuthenticationException;
import com.reagan.shopIt.util.JwtConfig;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;
    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(JwtConfig jwtConfig, UserDetailsService userDetailsService) {
        this.jwtConfig = jwtConfig;
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getExpiration() * 1000);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret())
                    .parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("Invalid or expired token", HttpStatus.UNAUTHORIZED);
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtConfig.getHeader());
        if (bearerToken != null && bearerToken.startsWith(jwtConfig.getPrefix())) {
            return bearerToken.substring(jwtConfig.getPrefix().length());
        }
        return null;
    }
}
