package com.spring.controller;

import com.spring.jwt.Jwt_Util;
import com.spring.jwt.LoginRequest;
import com.spring.model.User;
import com.spring.model.UserShow;
import com.spring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private Jwt_Util jwtUtil;

    @Autowired
    private UserRepository userRepository; // Fetch user manually

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Find user by username
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Generate JWT token
        UserShow userDetails = new UserShow(user);
        String token = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(Collections.singletonMap("token", token)); // Return JWT token
    }
}
    