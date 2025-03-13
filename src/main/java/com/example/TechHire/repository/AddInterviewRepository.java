package com.example.TechHire.repository;

import com.example.TechHire.entity.AddInterview;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AddInterviewRepository extends MongoRepository<AddInterview, String> {
    List<AddInterview> findByCandidateId(String candidateId);
    List<AddInterview> findByJobId(String jobId);
    List<AddInterview> findByStatus(String status);
}
