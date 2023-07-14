package com.reagan.shopIt.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;


public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String message) {
        super(message);
    }

    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtAuthenticationException(String invalidOrExpiredToken, HttpStatus unauthorized) {
        super(invalidOrExpiredToken);
    }
}
