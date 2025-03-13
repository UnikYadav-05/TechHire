package com.example.TechHire.repository;

import com.example.TechHire.entity.AddMember;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AddMemberRepository extends MongoRepository<AddMember, String> {
    List<AddMember> findByDesignation(String designation); // Custom method to find members by designation
}
