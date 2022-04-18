package com.example.confirmemail.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registration")
public class  RegistrationUserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    private Date expiryDate;

    public RegistrationUserToken(String token, User user) {
        this.token = token;
        this.user = user;

        //1h
        expiryDate = new Date(System.currentTimeMillis() + 360000);
    }
}
