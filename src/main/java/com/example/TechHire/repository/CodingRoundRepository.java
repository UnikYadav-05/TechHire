package com.example.TechHire.repository;

import com.example.TechHire.entity.CodingRound;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CodingRoundRepository extends MongoRepository<CodingRound, String> {
    List<CodingRound> findByJobId(String jobId);
    List<CodingRound> findByCandidateId(String candidateId);
}
