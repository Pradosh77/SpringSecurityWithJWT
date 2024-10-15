package com.example.springSecurityWebWithJWT.repositories;

import com.example.springSecurityWebWithJWT.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);
}
