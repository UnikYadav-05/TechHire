package com.example.TechHire.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "coding_round")
@Data
public class CodingRound {
    @Id
    private String id;
    private String candidateId;
    private String jobId;
    private String jobAppliedId;
    private String status; // "Pending", "Completed", "Failed"
    private String appliedFor;
    private LocalDate codingTestDate;
    private LocalTime codingTestStartTime;
    private LocalTime codingTestEndTime;
    private LocalTime codingTestDeadline;
    private String codingPlatformUrl;
    private String instructions;
    private Double score;
    private String candidateName;
    private String candidateEmail;
    private String phoneNumber;

    public CodingRound(String candidateId, String jobId, String jobAppliedId, String appliedFor,
                       LocalDate codingTestDate, LocalTime codingTestStartTime,
                       LocalTime codingTestEndTime, LocalTime codingTestDeadline,
                       String codingPlatformUrl, String instructions, Double score,
                       String candidateName, String candidateEmail, String phoneNumber) {
        this.candidateId = candidateId;
        this.jobId = jobId;
        this.jobAppliedId = jobAppliedId;
        this.appliedFor = appliedFor;
        this.codingTestDate = codingTestDate;
        this.codingTestStartTime = codingTestStartTime;
        this.codingTestEndTime = codingTestEndTime;
        this.codingTestDeadline = codingTestDeadline;
        this.codingPlatformUrl = codingPlatformUrl;
        this.instructions = instructions;
        this.status = "Coding_Round";
        this.score = 0.0;
        this.candidateName = candidateName;
        this.candidateEmail = candidateEmail;
        this.phoneNumber = phoneNumber;
    }
}
