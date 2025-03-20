package com.example.TechHire.repository;

import com.example.TechHire.entity.OnlineAssessment;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OnlineAssessmentRepository extends MongoRepository<OnlineAssessment, String> {
    List<OnlineAssessment> findByJobId(String jobId);
    List<OnlineAssessment> findByCandidateId(String candidateId);
}
