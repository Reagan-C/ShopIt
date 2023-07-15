package com.reagan.shopIt.config.filter;

import com.reagan.shopIt.model.exception.JwtAuthenticationException;
import com.reagan.shopIt.model.exception.SetAuthenticationFailureException;
import com.reagan.shopIt.config.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtTokenProvider tokenProvider;

    @Autowired
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

            String tokenHeader = request.getHeader("Authorization");
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !StringUtils.startsWith(tokenHeader,"Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        String username;
        try {
            username = tokenProvider.getUsernameFromToken(token);
        } catch (IllegalArgumentException | JwtException ex) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        if (StringUtils.isEmpty(username)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (tokenProvider.validateToken(token, userDetails)) {
                try {
                    UsernamePasswordAuthenticationToken jwtToken
                            = new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());
                    jwtToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(jwtToken);
                } catch (Exception e) {
                    throw new SetAuthenticationFailureException();
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}


