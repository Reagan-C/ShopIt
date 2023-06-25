package com.reagan.shopIt.service;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    void sendWelcomeMessage(String email);

    @Async
    void sendAccountConfirmationMail(String emailAddress, String otp);

    @Async
    void sendChangePasswordMail(String email, String otp);

    void sendOrderTrackingMail(String email, String orderTrackingCode);

    void sendOrderDeliveryMail(String email, String orderTrackingCode);

}
