package com.reagan.shopIt.config.security.jwt;

import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.domain.UserRole;
import com.reagan.shopIt.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public JwtAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findByUsername(username);
        if (user!= null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, password, getUserRoles(user.getRoles()));
            }
        }
        throw new BadCredentialsException("Username or password incorrect");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private Collection<? extends GrantedAuthority> getUserRoles(Set<UserRole> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRole role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getCode()));
        }
        return authorities;
    }
}
