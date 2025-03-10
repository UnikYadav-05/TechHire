package com.example.TechHire.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "job_applied")
@Data
public class JobApplication {
    private String jobId;       // Fetched from job posting
    private String candidateId; // Fetched from candidate profile
    private String name;
    private String email;
    private String resumeUrl;
    private String address;
    private String phoneNumber;
}
