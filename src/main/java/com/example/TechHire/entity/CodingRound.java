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
    private String applicationId;
    private String status; // "Pending", "Completed", "Failed"
    private LocalDate codingTestDate;
    private LocalTime codingTestStartTime;
    private LocalTime codingTestEndTime;
    private LocalTime codingTestDeadline;
    private String codingPlatformUrl;
    private String instructions;

    public CodingRound(String candidateId, String jobId, String applicationId,
                       LocalDate codingTestDate, LocalTime codingTestStartTime,
                       LocalTime codingTestEndTime, LocalTime codingTestDeadline,
                       String codingPlatformUrl, String instructions) {
        this.candidateId = candidateId;
        this.jobId = jobId;
        this.applicationId = applicationId;
        this.codingTestDate = codingTestDate;
        this.codingTestStartTime = codingTestStartTime;
        this.codingTestEndTime = codingTestEndTime;
        this.codingTestDeadline = codingTestDeadline;
        this.codingPlatformUrl = codingPlatformUrl;
        this.instructions = instructions;
        this.status = "Pending";
    }
}
