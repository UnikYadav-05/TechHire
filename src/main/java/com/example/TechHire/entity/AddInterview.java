package com.example.TechHire.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Document(collection = "add_interview") // Collection name in MongoDB
@Data
public class AddInterview {

    @Id
    private String id; // Auto-generated unique ID

    private String candidateId; // Reference to Candidate
    private String name; // Candidate's name
    private String interviewMode; // Online/Offline

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateInterview; // YYYY-MM-DD format (Correct format for MongoDB)

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime timeInterview; // HH:MM:SS format (Correct format for MongoDB)

    private String assignedManager; // Manager Name or ID
    private String meetingLink; // URL if online
    private List<String> interviewPanelMembers; // List of panelists
    private String status; // Scheduled/Completed/Cancelled
    private String jobId; // Related Job ID

    @CreatedDate
    private Instant createdAt; // Auto-generated timestamp

    @LastModifiedDate
    private Instant updatedAt; // Auto-updated timestamp
}
