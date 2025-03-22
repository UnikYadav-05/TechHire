package com.example.TechHire.service;

import com.example.TechHire.entity.Candidate;
import com.example.TechHire.entity.Notification;
import com.example.TechHire.repository.CandidateRepository;
import com.example.TechHire.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    // ✅ Send Notification
    public void sendNotification(String userId, String message) {
        Notification notification = new Notification(userId, message);
        notificationRepository.save(notification);
    }

    // ✅ Send Notification to All Candidates
    public void sendNotificationToAllCandidates(String message) {
        List<Candidate> candidates = candidateRepository.findAll();
        for (Candidate candidate : candidates) {
            Notification notification = new Notification(candidate.getId(), message);
            notificationRepository.save(notification);
        }
    }

    // ✅ Get Unread Notifications
    public List<Notification> getUnreadNotifications(String userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    // ✅ Get All Notifications (Read & Unread)
    public List<Notification> getAllNotifications(String userId) {
        return notificationRepository.findByUserId(userId);
    }

    // ✅ Mark Notifications as Read
    public void markNotificationsAsRead(List<String> notificationIds) {
        for (String id : notificationIds) {
            Optional<Notification> notificationOpt = notificationRepository.findById(id);
            notificationOpt.ifPresent(notification -> {
                notification.setRead(true);
                notificationRepository.save(notification);
            });
        }
    }

    // ✅ Delete Notification
    public void deleteNotification(String notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
