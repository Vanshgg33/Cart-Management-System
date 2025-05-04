package com.spring.controller;

import com.spring.jwt.Jwt_Util;
import com.spring.jwt.LoginRequest;
import com.spring.model.User;
import com.spring.model.UserShow;
import com.spring.repo.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

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
        ResponseCookie cookie = ResponseCookie.from("jwtToken", token)
                .httpOnly(true)
                .path("/")
                .maxAge(24 * 60 * 60) // 1 day
                .secure(false) // set to true in production (HTTPS)
                .sameSite("Lax")
                .build();


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Collections.singletonMap("token", token)); // Return JWT token
    }
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Missing or malformed Authorization header");
        }

        String token = authHeader.substring(7);
        try {
            boolean expired = jwtUtil.isTokenExpired(token);
            if (expired) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is expired");
            } else {

                ResponseCookie cookie = ResponseCookie.from("jwtToken", token)
                        .httpOnly(true)
                        .path("/")
                        .maxAge(24 * 60 * 60) // 1 day
                        .secure(false) // set to true in production (HTTPS)
                        .sameSite("Lax")
                        .build();

                return ResponseEntity.ok()
                        .header("Set-Cookie", cookie.toString())
                        .body("Token is valid and stored in cookie and token is valid ");

            }
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is expired");
        } catch (MalformedJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Malformed token");
        } catch (SignatureException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token signature");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or unreadable token");
        }
    }




    @GetMapping("/user")
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User oauth2User) {
        return oauth2User.getAttributes(); // Returns user details (including email)
    }
}
