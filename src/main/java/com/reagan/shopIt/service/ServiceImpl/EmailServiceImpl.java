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
    public void sendAccountConfirmationMail(String emailAddress, String content) {
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
        User user = userRepository.findByEmailAddress(email);
        String content = " <div style=\"min-width:1000px;overflow:auto;line-height:2\"><div style=\"margin:" +
                "50px auto;width:50%;padding:20px 0\"><div style=\"font-family:Helvetica,Arial,sans-serif;display:" +
                "flex;border-bottom:1px solid #eee;font-size:1.2em;\"><a href=\"\" style=\"margin-right: 5px;" +
                "color: #00466a;text-decoration:none;font-weight:600\"><img style=\"height:55px; width:55px\" " +
                "src=\"\" /></a><p>ShopIt</p></div><p style=\"font-size:1.1em\">" + "Dear " +
                user.getFirstName() + ", <br>" + "Your password reset code is " + otp + " and it is valid for \""
                + environment.getProperty("shopIt.otp.expiration")  + "\" <hr style=\"border:none;border-top:" +
                "1px solid #eee\" />" + "<div style=\"margin-top: 20px;padding" +
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
    public void sendSuccessfulPasswordChangeMail(String email) {
        User user = userRepository.findByEmailAddress(email);
        String content = " <div style=\"min-width:1000px;overflow:auto;line-height:2\">" +
                " <div style=\"margin:50px auto;width:50%;padding:20px 0\">" +
                "<div style=\"font-family:Helvetica,Arial,sans-serif;display:flex;border-bottom:1px solid #eee;" +
                "font-size:1.2em;\"> <a href=\"\" style=\"margin-right: 5px;color: #00466a;text-decoration:none;" +
                "font-weight:600\"><img style=\"height:55px; width:55px\" src=\"/\" /></a>" + "<p>ShopIt</p>" +
                "</div>" + "<p style=\"font-size:1.1em\">" + "<b>Dear " + user.getFirstName() + ",</b>" + "<br>" +
                "Your password change is successful" + "<br>" +
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
    public void sendOrderTrackingMail(String email, String orderTrackingCode) {
        User user = userRepository.findByEmailAddress(email);
        String content = " <div style=\"min-width:1000px;overflow:auto;line-height:2\">" +
                " <div style=\"margin:50px auto;width:50%;padding:20px 0\">" +
                "<div style=\"font-family:Helvetica,Arial,sans-serif;display:flex;border-bottom:1px solid #eee;" +
                "font-size:1.2em;\"> <a href=\"\" style=\"margin-right: 5px;color: #00466a;text-decoration:none;" +
                "font-weight:600\"><img style=\"height:55px; width:55px\" src=\"/\" /></a>" + "<p>ShopIt</p>" +
                "</div>" + "<p style=\"font-size:1.1em\">" + "<b>Hello " + user.getFirstName() + ",</b>" + "<br>" +
                "Your order has been placed and your order tracking code is " + orderTrackingCode + " ,and will " +
                "be delivered to your address, " +user.getAddress() + " within two weeks. <br>" +
                "Best regards, " + "<br>" + "ShopIt Team.</p>" +
                " <hr style=\"border:none;border-top:1px solid #eee\" />" +
                "<div style=\"margin-top: 20px;padding:8px 0;color:#aaa;font-size:1.0em;line-height:1;" +
                "font-weight:300\"> <p>© 2023 ShopIt, All rights reserved.</p></div></div></div>";

        String subject = "Order Tracking Code(NO REPLY)";
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
    public void sendOrderDeliveryMail(String email, String content) {

        User user = userRepository.findByEmailAddress(email);


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

    @Override
    public String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + "," +
                "</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. " +
                "Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;" +
                "border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\">" +
                "<p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a>" +
                " </p></blockquote>\n Link will expire in 20 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    @Override
    public String buildOrderConfirmationEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + "," +
                "</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" +
                "Please click on the below link to confirm your order reception: </p><blockquote style=\"Margin:0 0 20px 0;" +
                "border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\">" +
                "<p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Confirm Now</a>" +
                " </p></blockquote>\n <p>ShopIt team </p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
