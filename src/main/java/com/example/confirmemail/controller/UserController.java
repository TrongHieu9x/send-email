package com.example.confirmemail.controller;

import com.example.confirmemail.entity.User;
import com.example.confirmemail.service.impl.EmailService;
import com.example.confirmemail.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        //create user
        userService.save(user);

        return new ResponseEntity<>("We have sent an email. Please check email to active account!", HttpStatus.OK);
    }

    @GetMapping("/activeUser")
    public ResponseEntity<?> activeUserViaEmail(@RequestParam String token) {
        userService.activeUser(token);
        return new ResponseEntity<>("Active success!", HttpStatus.OK);
    }

    // resend confirm
    @GetMapping("/userRegistrationConfirmRequest")
    //validate: email exists, email not active
    public ResponseEntity<?> sendConfirmRegistrationViaEmail(@RequestParam String email) {
        userService.sendConfirmUserRegistrationViaEmail(email);

        return new ResponseEntity<>("We have sent 1 email. Please check email to active account!", HttpStatus.OK);
    }


}
