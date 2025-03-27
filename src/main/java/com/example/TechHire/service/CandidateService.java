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

    public Optional<Candidate> getCandidateByEmail(String email) {
        return candidateRepository.findByEmail(email);
    }

    public Candidate updateCandidate(String id, Candidate candidateDetails) {
        Optional<Candidate> optionalCandidate = candidateRepository.findById(id);

        if (optionalCandidate.isPresent()) {
            Candidate candidate = optionalCandidate.get();
            candidate.setName(candidateDetails.getName());
            candidate.setEmail(candidateDetails.getEmail());
            candidate.setPhoneNumber(candidateDetails.getPhoneNumber());
            candidate.setResumeUrl(candidateDetails.getResumeUrl());
            candidate.setSkills(candidateDetails.getSkills());
            candidate.setEducation(candidateDetails.getEducation());
            candidate.setExperience(candidateDetails.getExperience());
            candidate.setAddress(candidateDetails.getAddress());
            candidate.setCodingProfile(candidateDetails.getCodingProfile());
            candidate.setGithub(candidateDetails.getGithub());
            candidate.setLinkedin(candidateDetails.getLinkedin());
            return candidateRepository.save(candidate);
        } else {
            throw new RuntimeException("Candidate not found with ID: " + id);
        }
    }


    public void deleteCandidate(String id) {
        candidateRepository.deleteById(id);
    }
}
