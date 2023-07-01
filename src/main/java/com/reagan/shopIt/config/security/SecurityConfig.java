package com.reagan.shopIt.config.security;

import com.reagan.shopIt.config.filter.JwtAuthenticationFilter;
import com.reagan.shopIt.config.security.jwt.JwtAuthenticationEntryPoint;
import com.reagan.shopIt.config.security.jwt.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private final JwtAuthenticationProvider jwtAuthProvider;

    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    private  final JwtAuthenticationEntryPoint authEntryPoint;

    @Autowired
    private final JwtAuthenticationFilter authFilter;

    public SecurityConfig(JwtAuthenticationProvider jwtAuthProvider, UserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint authEntryPoint, JwtAuthenticationFilter authFilter) {
        this.jwtAuthProvider = jwtAuthProvider;
        this.authEntryPoint = authEntryPoint;
        this.authFilter = authFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http.csrf((AbstractHttpConfigurer::disable))
                    .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth ->
                            auth.requestMatchers("/api/v1/items/***").permitAll()
                                .requestMatchers("/api/v1/test/***").permitAll()
                                .anyRequest().authenticated()
                    );
            http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder managerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        managerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        managerBuilder.authenticationProvider(jwtAuthProvider);
        return managerBuilder.build();
    }
}
