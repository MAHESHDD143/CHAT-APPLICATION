package com.mahesh.messageservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahesh.messageservice.model.User;
import com.mahesh.messageservice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }
    public User registerUser(Map<String,String> allParams){
    	log.info("all params : {}",allParams);
        User user=new User();
        user.setUserName(allParams.get("userName"));
        user.setPassword(passwordEncoder.encode(allParams.get("password")));
        user.setEmail(allParams.get("email"));
        user.setJoinDate(LocalDateTime.now().toString());
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUserName(username);
    }
}
