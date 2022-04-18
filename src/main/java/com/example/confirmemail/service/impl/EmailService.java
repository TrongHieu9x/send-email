package com.example.confirmemail.service.impl;

import com.example.confirmemail.entity.User;
import com.example.confirmemail.repository.IRegistrationUserTokenRepository;
import com.example.confirmemail.service.IEmailService;
import com.example.confirmemail.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {
    @Autowired
    private IUserService userService;

    @Autowired
    public JavaMailSender mailSender;

    @Autowired
    private IRegistrationUserTokenRepository registrationUserToken;


    @Override
    public void SendRegistrationUserConfirm(String email) {
        User user = userService.findUserByEmail(email);

        String token = registrationUserToken.findByUserId(user.getId());

        String confirmationUrl = "http://localhost:8080/api/v1/users/activeUser?token=" + token;
        String subject = "Xác Nhận Đăng Ký Account";
        String content = "Bạn đã đăng kí thành công. Click vào link dưới đây để kích hoạt tài khoản \n" + confirmationUrl;

        sendEmail(email, subject, content);
    }

    private void sendEmail(final String recipientEmail, final String subject, final String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }
}
