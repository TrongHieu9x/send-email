package com.example.confirmemail.repository;

import com.example.confirmemail.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String userName);

    boolean existsByEmail(String email);

    @Query("	SELECT 	u.status 		"
            + "	FROM 	User u		"
            + " WHERE 	u.email = :email ")
    User findStatusByEmail(@Param("email") String email);

    User findByUsername(String userName);

    User findByEmail(String email);
}
