package com.example.TechHire.repository;

import com.example.TechHire.entity.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ApplicationRepository extends MongoRepository<Application, String> {
    List<Application> findByCandidateId(String candidateId);
    List<Application> findByJobId(String jobId);
    List<Application> findByStatus(String status);
}
