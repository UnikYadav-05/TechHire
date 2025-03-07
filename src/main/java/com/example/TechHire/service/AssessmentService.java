package com.example.TechHire.service;


import com.example.TechHire.entity.Assessment;
import com.example.TechHire.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository repository;

    public List<Assessment> getAllAssessments() {
        return repository.findAll();
    }

    public Optional<Assessment> getAssessmentById(String id) {
        return repository.findById(id);
    }

    public Assessment createAssessment(Assessment assessment) {
        return repository.save(assessment);
    }

    public Assessment updateAssessment(String id, Assessment updatedAssessment) {
        if (repository.existsById(id)) {
            updatedAssessment.setId(id);
            return repository.save(updatedAssessment);
        }
        return null;
    }

    public void deleteAssessment(String id) {
        repository.deleteById(id);
    }
}

