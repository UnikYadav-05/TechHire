package com.example.TechHire.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "online_assessment")
@Data
public class OnlineAssessment {
    @Id
    private String id;
    private String candidateId;
    private String jobId;
    private String jobAppliedId;
    private String appliedFor;
    private String status; // "Pending", "Completed", "Expired"
    private LocalDate testDate;
    private LocalTime testDeadline;
    private String attachments;
    private String type_of_test;
    private Double score;
    private String candidateName;
    private String candidateEmail;
    private String phoneNumber;

    public OnlineAssessment(String candidateId, String jobId, String jobAppliedId, String appliedFor,
                            LocalDate testDate, LocalTime testDeadline, String attachments , String type_of_test,
                            String candidateName, String candidateEmail, String phoneNumber) {
        this.candidateId = candidateId;
        this.jobId = jobId;
        this.jobAppliedId = jobAppliedId;
        this.appliedFor = appliedFor;
        this.testDate = testDate;
        this.testDeadline = testDeadline;
        this.attachments = attachments;
        this.type_of_test = type_of_test;
        this.candidateName = candidateName;
        this.candidateEmail = candidateEmail;
        this.phoneNumber = phoneNumber;
        this.status = "Pending"; // Default status
        this.score = 0.0;
    }
}
