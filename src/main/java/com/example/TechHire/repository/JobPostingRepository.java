package com.example.TechHire.repository;

import com.example.TechHire.entity.JobPosting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobPostingRepository extends MongoRepository<JobPosting, String> {
    List<JobPosting> findByHrId(String hrId); // Find all jobs posted by a specific HR
}
