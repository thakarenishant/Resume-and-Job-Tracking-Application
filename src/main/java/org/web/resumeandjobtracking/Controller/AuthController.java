package org.web.resumeandjobtracking.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.web.resumeandjobtracking.DTO.LoginRequest;
import org.web.resumeandjobtracking.DTO.UserRequest;
import org.web.resumeandjobtracking.Model.User;
import org.web.resumeandjobtracking.SecurityConfigurations.SecurityConfig;
import org.web.resumeandjobtracking.Service.UserService;
import org.web.resumeandjobtracking.Service.customUserDetailsService;

@RestController
public class AuthController {
    @Autowired
    private UserService service;
   @Autowired
   private PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public ResponseEntity<String> signup(@RequestBody UserRequest request) {
        System.out.println("register method called");
        service.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest user){
        boolean saved= service.loginUser(user);

        return saved?ResponseEntity.ok("User logged in successfully"):ResponseEntity.badRequest().body("Invalid username or password");
    }

}
