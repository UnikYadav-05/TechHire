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
    private LocalTime testStartTime;
    private LocalTime testEndTime;
    private LocalTime testDeadline;

    public OnlineAssessment(String candidateId, String jobId, String jobAppliedId, String appliedFor,LocalDate testDate, LocalTime testStartTime, LocalTime testEndTime, LocalTime testDeadline) {
        this.candidateId = candidateId;
        this.jobId = jobId;
        this.jobAppliedId = jobAppliedId;
        this.testDate = testDate;
        this.appliedFor = appliedFor;
        this.testStartTime = testStartTime;
        this.testEndTime = testEndTime;
        this.testDeadline = testDeadline;
        this.status = "Pending"; // Default status
    }
}
