package com.example.TechHire.repository;

import com.example.TechHire.entity.ShortlistCandidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ShortlistCandidateRepository extends MongoRepository<ShortlistCandidate, String> {
    List<ShortlistCandidate> findByJobId(String jobId);
    List<ShortlistCandidate> findByCandidateId(String candidateId);

    boolean existsByJobAppliedId(String jobAppliedId);
}
