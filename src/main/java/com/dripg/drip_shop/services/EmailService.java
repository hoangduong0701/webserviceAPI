package com.dripg.drip_shop.services;

import com.dripg.drip_shop.auth.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
//ok
@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendEmail(User user) {
        String senderName = "DripG";
        String subject = "Xác minh email của bạn";
        String mailContent = "Xin chào" + user.getUsername() +"\n";
        mailContent += "Mã xác nhận của bạn là: " + user.getVerificationCode() + "\n";
        mailContent += "Vui lòng nhập xác minh!";
        mailContent += "\n";
        mailContent += senderName;


        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(user.getEmail());
            message.setText(mailContent);
            message.setSubject(subject);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "email sent";
    }
}
