package com.example.TechHire.service;

import com.example.TechHire.entity.Application;
import com.example.TechHire.entity.Candidate;
import com.example.TechHire.entity.JobApplication;
import com.example.TechHire.entity.ShortlistCandidate;
import com.example.TechHire.repository.ApplicationRepository;
import com.example.TechHire.repository.CandidateRepository;
import com.example.TechHire.repository.JobApplicationRepository;
import com.example.TechHire.repository.ShortlistCandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortlistCandidateService {

    @Autowired
    private JobApplicationRepository applicationRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ShortlistCandidateRepository shortlistCandidateRepository;

    // HR Shortlists a Candidate
    public ShortlistCandidate shortlistCandidate(String jobAppliedId) {
        // Check if the application is already shortlisted
        if (shortlistCandidateRepository.existsByJobAppliedId(jobAppliedId)) {
            throw new RuntimeException("Candidate is already shortlisted for this application.");
        }

        JobApplication application = applicationRepository.findById(jobAppliedId)
                .orElseThrow(() -> new RuntimeException("JobApplication not found"));

        Candidate candidate = candidateRepository.findById(application.getCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        ShortlistCandidate shortlist = new ShortlistCandidate(
                jobAppliedId,
                candidate.getId(),
                application.getJobId(),
                candidate.getName(),
                candidate.getMail(),
                candidate.getResume()
        );

        application.setStatus("Shortlisted");
        applicationRepository.save(application); // Update status in applications
        return shortlistCandidateRepository.save(shortlist); // Save in shortlist_candidate collection
    }

    // Get all shortlisted candidates
    public List<ShortlistCandidate> getAllShortlistedCandidates() {
        return shortlistCandidateRepository.findAll();
    }

    // Get shortlisted candidates by job
    public List<ShortlistCandidate> getShortlistedByJob(String jobId) {
        return shortlistCandidateRepository.findByJobId(jobId);
    }

    // Get shortlisted candidates by candidate
    public List<ShortlistCandidate> getShortlistedByCandidate(String candidateId) {
        return shortlistCandidateRepository.findByCandidateId(candidateId);
    }
}
