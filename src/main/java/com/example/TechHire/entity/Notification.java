package com.example.TechHire.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;
    private String userId;
    private String message;
    private boolean isRead;
    private LocalDateTime timestamp;

    public Notification(String userId, String message) {
        this.userId = userId;
        this.message = message;
        this.isRead = false;
        this.timestamp = LocalDateTime.now();
    }
}
