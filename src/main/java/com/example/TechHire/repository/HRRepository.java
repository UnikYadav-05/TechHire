package com.example.TechHire.repository;

import com.example.TechHire.entity.HR;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface HRRepository extends MongoRepository<HR, String> {
    Optional<HR> findByEmail(String email); // Find HR by email
}
