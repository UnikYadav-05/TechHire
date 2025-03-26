package com.example.TechHire.controller;

import com.example.TechHire.entity.Notification;
import com.example.TechHire.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // 🔹 Get Unread Notifications
    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable String userId) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(userId));
    }

    // 🔹 Get All Notifications
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Notification>> getAllNotifications(@PathVariable String userId) {
        return ResponseEntity.ok(notificationService.getAllNotifications(userId));
    }

    // 🔹 Mark Notifications as Read
    @PostMapping("/read")
    public ResponseEntity<String> markNotificationsAsRead(@RequestBody Map<String, List<String>> request) {
        notificationService.markNotificationsAsRead(request.get("notificationIds"));
        return ResponseEntity.ok("✅ Notifications marked as read.");
    }

    // 🔹 Send Notification to a User (Real-Time & Stored)
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String message = request.get("message");

        if (userId == null || message == null) {
            return ResponseEntity.badRequest().body("❌ Missing userId or message.");
        }

        notificationService.sendNotification(userId, message);
        return ResponseEntity.ok("✅ Notification sent successfully.");
    }

    // 🔹 Send Notification to All Candidates (Real-Time & Stored)
    @PostMapping("/sendToAll")
    public ResponseEntity<String> sendNotificationToAllCandidates(@RequestBody Map<String, String> request) {
        String message = request.get("message");

        if (message == null) {
            return ResponseEntity.badRequest().body("❌ Missing message.");
        }

        notificationService.sendNotificationToAllCandidates(message);
        return ResponseEntity.ok("✅ Notification sent to all candidates.");
    }

    // 🔹 Delete Notification
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable String notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok("✅ Notification deleted successfully.");
    }
}
