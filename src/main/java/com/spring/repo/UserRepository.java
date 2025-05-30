package com.spring.repo;

import com.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("SELECT u FROM User u WHERE u.username = :input OR u.email = :input OR u.PhoneNumber = :input")
    User findByUsernameOrEmailOrPhone(@Param("input") String input);
}

