package com.reagan.shopIt.util;

import com.reagan.shopIt.config.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OTPGeneratorImpl implements OTPGenerator{

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public String createVerificationToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String createPasswordResetToken() {
        return  UUID.randomUUID().toString();
    }

    @Override
    public String createOrderTrackingCode() {
        return UUID.randomUUID().toString();
    }
}
