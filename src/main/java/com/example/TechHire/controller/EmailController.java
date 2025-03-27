package com.example.TechHire.controller;

import com.example.TechHire.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // ✅ Send Email with Custom Sender & Recipient
    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody Map<String, String> request) {
        String fromEmail = request.get("fromEmail");
        String toEmail = request.get("toEmail");
        String subject = request.get("subject");
        String body = request.get("body");

        if (fromEmail == null || toEmail == null || subject == null || body == null) {
            return ResponseEntity.badRequest().body("❌ Missing required parameters: fromEmail, toEmail, subject, or body.");
        }

        try {
            emailService.sendEmail(fromEmail, toEmail, subject, body);
            return ResponseEntity.ok("✅ Email sent successfully.");
        } catch (MessagingException e) {
            return ResponseEntity.internalServerError().body("❌ Failed to send email: " + e.getMessage());
        }
    }
}
