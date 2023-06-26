package com.reagan.shopIt.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OTPGeneratorImpl implements OTPGenerator{

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
