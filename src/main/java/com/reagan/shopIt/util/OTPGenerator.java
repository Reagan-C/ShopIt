package com.reagan.shopIt.util;

public interface OTPGenerator {

    String createVerificationToken();

    String createPasswordResetToken();

    String createOrderTrackingCode();
}
