package com.example.confirmemail.event;

import com.example.confirmemail.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SendRegistrationUserConfirmViaEmailListener implements ApplicationListener<OnSendRegistrationUserConfirmEvent> {
    @Autowired
    private IEmailService emailService;

    private void sendConfirmViaEmail(String email) {
        emailService.SendRegistrationUserConfirm(email);
    }

    @Override
    public void onApplicationEvent(OnSendRegistrationUserConfirmEvent event) {
        sendConfirmViaEmail(event.getEmail());
    }
}
