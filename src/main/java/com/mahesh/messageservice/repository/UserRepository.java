package com.mahesh.messageservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahesh.messageservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);

}
