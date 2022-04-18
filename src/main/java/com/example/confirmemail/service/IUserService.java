package com.example.confirmemail.service;

import com.example.confirmemail.entity.User;

import java.util.Optional;

public interface IUserService {
    Iterable<User> findAllUser();

    Optional<User> findUserById(long id);

    void save(User user);

    void deleteById(long id);

    User findUserByEmail(String email);

    void activeUser(String token);

    void sendConfirmUserRegistrationViaEmail(String email);

//    void OnResetPasswordViaEmailEvent(User user, String token);
}
