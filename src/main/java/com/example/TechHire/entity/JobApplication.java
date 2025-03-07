package com.example.TechHire.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "job_applied")
@Data
public class JobApplication {

    @Id
    private String candidateId;

    private String jobId;
    private String candidateName;
    private String candidateMail;
    private String resume; // Store file path or base64 string
    private String address;
    private String phoneNumber;
}
