package com.example.TechHire.repository;

import com.example.TechHire.entity.JobPosting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostingRepository extends MongoRepository<JobPosting, String> {
}

