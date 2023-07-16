package com.reagan.shopIt.service;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    void sendWelcomeMessage(String email, String name);

    @Async
    void sendAccountConfirmationMail(String emailAddress, String otp);

    @Async
    void sendChangePasswordMail(String email, String name, String otp);

    @Async
    void sendSuccessfulPasswordChangeMail(String name, String email);

    @Async
    void sendOrderTrackingMail(String email, String name, String address, String orderTrackingCode, double cost);

    @Async
    void sendOrderDeliveryMail(String email, String orderTrackingCode);

    String buildEmail(String name, String link);

    String buildOrderConfirmationEmail(String name, String link);

}
