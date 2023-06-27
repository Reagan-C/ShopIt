package com.reagan.shopIt.util;

import org.springframework.http.ResponseEntity;

public interface OTPGenerator {

    String createVerificationToken();

    String createPasswordResetToken();

    String createOrderTrackingCode();

}
