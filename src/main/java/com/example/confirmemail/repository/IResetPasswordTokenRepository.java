package com.example.confirmemail.repository;

import com.example.confirmemail.entity.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
    ResetPasswordToken findByToken(String token);

    boolean existsByToken(String token);

    @Transactional
    @Modifying
    @Query("delete from RegistrationUserToken r  where r.user.id = :userId")
    public void deleteByUserId(int userId);
}
