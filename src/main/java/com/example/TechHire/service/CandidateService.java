package com.example.TechHire.service;

import com.example.TechHire.entity.Candidate;
import com.example.TechHire.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public Candidate createCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Optional<Candidate> getCandidateById(String id) {
        return candidateRepository.findById(id);
    }

    public Candidate updateCandidate(String id, Candidate candidateDetails) {
        Optional<Candidate> optionalCandidate = candidateRepository.findById(id);

        if (optionalCandidate.isPresent()) {
            Candidate candidate = optionalCandidate.get();
            candidate.setName(candidateDetails.getName());
            candidate.setMail(candidateDetails.getMail());
            candidate.setPhoneNumber(candidateDetails.getPhoneNumber());
            candidate.setResumeUrl(candidateDetails.getResumeUrl());
            candidate.setSkills(candidateDetails.getSkills());
            candidate.setEducation(candidateDetails.getEducation());
            candidate.setExperience(candidateDetails.getExperience());
            candidate.setCoverLetter(candidateDetails.getCoverLetter());
            candidate.setAddress(candidateDetails.getAddress());
            candidate.setJobApplied(candidateDetails.getJobApplied());

            return candidateRepository.save(candidate);
        } else {
            throw new RuntimeException("Candidate not found with ID: " + id);
        }
    }


    public void deleteCandidate(String id) {
        candidateRepository.deleteById(id);
    }
}
