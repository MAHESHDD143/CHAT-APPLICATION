package com.mahesh.messageservice.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahesh.messageservice.model.User;
import com.mahesh.messageservice.service.JwtService;
import com.mahesh.messageservice.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth")
public class AuthController {
	private final UserService userService;
	private final JwtService jwtService;

	public AuthController(UserService userService, JwtService jwtService) {
		this.userService = userService;
		this.jwtService = jwtService;
	}
	 @PostMapping("/register")
	    public User register(@RequestBody Map<String,String> allParams){
	        return userService.registerUser(allParams);
	    }

	    @PostMapping("/login")
	    public String login(@RequestBody Map<String,String> request){
	        User user = userService.findByUsername(request.get("userName"))
	                .orElseThrow(() -> new RuntimeException("User not found"));
	        return jwtService.generateToken(user.getUserName());
	    }
}
