package com.reagan.shopIt.service.ServiceImpl;

import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.exception.EmailDeliveryException;
import com.reagan.shopIt.repository.UserRepository;
import com.reagan.shopIt.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@PropertySource(value = "classpath:application.properties")
@Configuration
public class EmailServiceImpl implements EmailService {

    @Autowired
    private final JavaMailSender mailSender;

    @Autowired
    private final UserRepository userRepository;

    private Environment environment;

    public EmailServiceImpl(JavaMailSender mailSender, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    @Override
    public void sendWelcomeMessage(String email) {
        User user = userRepository.findByEmailAddress(email);
        String content = " <div style=\"min-width:1000px;overflow:auto;line-height:2\">" +
                " <div style=\"margin:50px auto;width:50%;padding:20px 0\">" +
                "<div style=\"font-family:Helvetica,Arial,sans-serif;display:flex;border-bottom:1px solid #eee;" +
                "font-size:1.2em;\"> <a href=\"\" style=\"margin-right: 5px;color: #00466a;text-decoration:none;" +
                "font-weight:600\"><img style=\"height:55px; width:55px\" src=\"/\" /></a>" + "<p>ShopIt</p>" +
                "</div>" + "<p style=\"font-size:1.1em\">" + "<b>Hello " + user.getFirstName() + ",</b>" + "<br>" +
                "Welcome to ShopIt." + "<br>" + "ShopIt is an E-commerce service where you shop for your needs." +
                " All you need to do is to select what you want and we'll have it delivered to your doorstep.</p>" +
                " <hr style=\"border:none;border-top:1px solid #eee\" />" +
                "<div style=\"margin-top: 20px;padding:8px 0;color:#aaa;font-size:1.0em;line-height:1;" +
                "font-weight:300\"> <p>© 2023 ShopIt, All rights reserved.</p></div></div></div>";

        String subject = "Welcome to ShopIt";
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(environment.getProperty("shopIt.email"));
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailDeliveryException();
        }
    }

    @Override
    public void sendAccountConfirmationMail(String emailAddress, String otp) {

        String content = " <div style=\"min-width:1000px;overflow:auto;line-height:2\"><div style=\"margin:" +
                "50px auto;width:50%;padding:20px 0\"><div style=\"font-family:Helvetica,Arial,sans-serif;display:" +
                "flex;border-bottom:1px solid #eee;font-size:1.2em;\"><a href=\"\" style=\"margin-right: 5px;" +
                "color: #00466a;text-decoration:none;font-weight:600\"><img style=\"height:55px; width:55px\" " +
                "src=\"\" /></a><p>ShopIt</p></div><p style=\"font-size:1.1em\">" + "Your account confirmation " +
                "code is " + otp + " and it is valid for \"" + environment.getProperty("shopIt.otp.expiration")  +
                "\" <hr style=\"border:none;border-top:1px solid #eee\" />" + "<div style=\"margin-top: 20px;padding" +
                ":8px 0;color:#aaa;font-size:1.0em;line-height:1;font-weight:300\">" +
                " <p>© 2023 ShopIt, All rights reserved.</p>" +
                " </div>" +
                "</div>" +
                "</div>";
        String subject = "Account confirmation (NO REPLY)";
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(environment.getProperty("shopIt.email"));
            helper.setTo(emailAddress);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailDeliveryException();
        }
    }

    @Override
    public void sendChangePasswordMail(String email, String otp) {

        String content = " <div style=\"min-width:1000px;overflow:auto;line-height:2\"><div style=\"margin:" +
                "50px auto;width:50%;padding:20px 0\"><div style=\"font-family:Helvetica,Arial,sans-serif;display:" +
                "flex;border-bottom:1px solid #eee;font-size:1.2em;\"><a href=\"\" style=\"margin-right: 5px;" +
                "color: #00466a;text-decoration:none;font-weight:600\"><img style=\"height:55px; width:55px\" " +
                "src=\"\" /></a><p>ShopIt</p></div><p style=\"font-size:1.1em\">" + "Your reset password " +
                "code is " + otp + " and it is valid for \"" + environment.getProperty("shopIt.otp.expiration")  +
                "\" <hr style=\"border:none;border-top:1px solid #eee\" />" + "<div style=\"margin-top: 20px;padding" +
                ":8px 0;color:#aaa;font-size:1.0em;line-height:1;font-weight:300\">" +
                " <p>© 2023 ShopIt, All rights reserved.</p>" +
                " </div>" +
                "</div>" +
                "</div>";
        String subject = "Password Reset (NO REPLY)";
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(environment.getProperty("shopIt.email"));
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailDeliveryException();
        }
    }

    @Override
    public void sendOrderTrackingMail(String email, String orderTrackingCode) {
        User user = userRepository.findByEmailAddress(email);
        String content = " <div style=\"min-width:1000px;overflow:auto;line-height:2\">" +
                " <div style=\"margin:50px auto;width:50%;padding:20px 0\">" +
                "<div style=\"font-family:Helvetica,Arial,sans-serif;display:flex;border-bottom:1px solid #eee;" +
                "font-size:1.2em;\"> <a href=\"\" style=\"margin-right: 5px;color: #00466a;text-decoration:none;" +
                "font-weight:600\"><img style=\"height:55px; width:55px\" src=\"/\" /></a>" + "<p>ShopIt</p>" +
                "</div>" + "<p style=\"font-size:1.1em\">" + "<b>Hello " + user.getFirstName() + ",</b>" + "<br>" +
                "Your order has been placed and your order tracking code is " + orderTrackingCode + ". <br>" +
                "Best regards, " + "<br>" + "ShopIt Team.</p>" +
                " <hr style=\"border:none;border-top:1px solid #eee\" />" +
                "<div style=\"margin-top: 20px;padding:8px 0;color:#aaa;font-size:1.0em;line-height:1;" +
                "font-weight:300\"> <p>© 2023 ShopIt, All rights reserved.</p></div></div></div>";

        String subject = "Order Tracking Code";
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(environment.getProperty("shopIt.email"));
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailDeliveryException();
        }
    }

    @Override
    public void sendOrderDeliveryMail(String email, String orderTrackingCode) {

        User user = userRepository.findByEmailAddress(email);
        String content = " <div style=\"min-width:1000px;overflow:auto;line-height:2\">" +
                " <div style=\"margin:50px auto;width:50%;padding:20px 0\">" +
                "<div style=\"font-family:Helvetica,Arial,sans-serif;display:flex;border-bottom:1px solid #eee;" +
                "font-size:1.2em;\"> <a href=\"\" style=\"margin-right: 5px;color: #00466a;text-decoration:none;" +
                "font-weight:600\"><img style=\"height:55px; width:55px\" src=\"/\" /></a>" + "<p>ShopIt</p>" +
                "</div>" + "<p style=\"font-size:1.1em\">" + "<b>Hello " + user.getFirstName() + ",</b>" + "<br>" +
                "Your order with order tracking code " + orderTrackingCode + " has been delivered. We look forward to " +
                "doing more business with you" + " <br>" +
                "Best regards, " + "<br>" + "ShopIt Team.</p>" +
                " <hr style=\"border:none;border-top:1px solid #eee\" />" +
                "<div style=\"margin-top: 20px;padding:8px 0;color:#aaa;font-size:1.0em;line-height:1;" +
                "font-weight:300\"> <p>© 2023 ShopIt, All rights reserved.</p></div></div></div>";

        String subject = "Order Delivery";
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(environment.getProperty("shopIt.email"));
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailDeliveryException();
        }
    }

}
