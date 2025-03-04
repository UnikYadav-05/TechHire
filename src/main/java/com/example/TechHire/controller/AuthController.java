package com.example.TechHire.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.TechHire.service.AuthService;
import com.example.TechHire.model.LoginRequest;
import com.example.TechHire.model.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // 1️⃣ Login API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }


    // 2️⃣ Register API
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        System.out.println("🚀 Register API hit");

        if (registerRequest == null) {
            System.out.println("❌ ERROR: RegisterRequest is NULL");
            return ResponseEntity.badRequest().body("Invalid request!");
        }

        System.out.println("Received Data: " + registerRequest.toString());

        return authService.registerUser(registerRequest);
    }


}