package com.example.TechHire.repository;

import com.example.TechHire.entity.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends MongoRepository<Candidate, String> {

}
