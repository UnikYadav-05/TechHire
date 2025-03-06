package com.example.TechHire.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.TechHire.service.AuthService;
import com.example.TechHire.model.LoginRequest;
import com.example.TechHire.model.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ✅ Login API (No Authorization Required)
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }


    // 🔓 2️⃣ Public Register API
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest);
    }

    // 👇 Fixed role names to match Keycloak expected format
    @GetMapping("/candidate/dashboard")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<String> candidateDashboard() {
        return ResponseEntity.ok("Candidate Dashboard: Apply for jobs, track applications.");
    }

    @GetMapping("/hr/dashboard")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<String> hrDashboard() {
        return ResponseEntity.ok("HR Dashboard: Post jobs, manage applications.");
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('HR', 'CANDIDATE')")
    public ResponseEntity<String> userProfile() {
        return ResponseEntity.ok("Profile Data: Accessible by both HR & Candidate.");
    }
}
