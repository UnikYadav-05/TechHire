package com.example.TechHire.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shortlist_candidate")
@Data
public class ShortlistCandidate {
    @Id
    private String id; // Auto-generated by MongoDB
    private String jobAppliedId; // Added to track which application was shortlisted
    private String candidateId;
    private String appliedFor;
    private String jobId;
    private String status; // Always "Shortlisted"
    private String candidateName;
    private String candidateEmail;
    private String resumeUrl;

    public ShortlistCandidate(String jobAppliedId, String candidateId, String appliedFor ,String jobId, String candidateName, String candidateEmail, String resumeUrl) {
        this.jobAppliedId = jobAppliedId;
        this.candidateId = candidateId;
        this.jobId = jobId;
        this.appliedFor = appliedFor;
        this.candidateName = candidateName;
        this.candidateEmail = candidateEmail;
        this.resumeUrl = resumeUrl;
        this.status = "Shortlisted"; // Default status
    }
}
