package com.example.confirmemail.service.impl;

import com.example.confirmemail.entity.RegistrationUserToken;
import com.example.confirmemail.entity.User;
import com.example.confirmemail.event.OnSendRegistrationUserConfirmEvent;
import com.example.confirmemail.repository.IRegistrationUserTokenRepository;
import com.example.confirmemail.repository.IResetPasswordTokenRepository;
import com.example.confirmemail.repository.IUserRepository;
import com.example.confirmemail.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private IResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    private IRegistrationUserTokenRepository registrationUserTokenRepository;

    @Override
    public void save(User user) {
        //create user
        userRepository.save(user);

        //create new user registration token
        createNewRegistrationUserToken(user);

        //send mail to confirm
        sendConfirmUserRegistrationViaEmail(user.getEmail());
    }

    private void createNewRegistrationUserToken(User user) {
        final String newToken = UUID.randomUUID().toString();

        RegistrationUserToken token = new RegistrationUserToken(newToken, user);
        registrationUserTokenRepository.save(token);
    }

    public void sendConfirmUserRegistrationViaEmail(String email) {
        eventPublisher.publishEvent(new OnSendRegistrationUserConfirmEvent(email));
    }

//    @Override
//    public void OnResetPasswordViaEmailEvent(User user, String token) {
//        String confirmationUrl = serverProperty.getUrl() + "/pi/v1/Users/resetPassword?token=" + token;
//
//        sendEmail(user.getEmail(),messageProperty.getMessage("Email.resetPassword.subject"),
//                messageProperty.getMessage("Email.resetPassword.message") + "\r\n" + confirmationUrl);
//    }

    @Override
    public void activeUser(String token) {
        RegistrationUserToken registrationUserToken = registrationUserTokenRepository.findByToken(token);

        User user = registrationUserToken.getUser();
        user.setStatus(true);

        userRepository.save(user);

        //remove registration user token
        registrationUserTokenRepository.deleteById(registrationUserToken.getId());
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Iterable<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
