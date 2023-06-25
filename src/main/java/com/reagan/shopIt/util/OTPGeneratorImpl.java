package com.reagan.shopIt.util;

import java.util.UUID;

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
