package com.example.TechHire.repository;


import com.example.TechHire.entity.Assessment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentRepository extends MongoRepository<Assessment, String> {
}

