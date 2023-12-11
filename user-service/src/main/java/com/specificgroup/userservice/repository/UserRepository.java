package com.specificgroup.userservice.repository;

import com.specificgroup.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findUserByUsername(String username);
    User findUserByUsernameAndPassword(String username, String password);
}
