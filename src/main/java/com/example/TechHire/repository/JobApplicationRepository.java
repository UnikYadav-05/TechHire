package com.example.TechHire.repository;

import com.example.TechHire.entity.JobApplication;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface JobApplicationRepository extends MongoRepository<JobApplication, String> {
    List<JobApplication> findByCandidateId(String candidateId);
    List<JobApplication> findByJobId(String jobId);
}
