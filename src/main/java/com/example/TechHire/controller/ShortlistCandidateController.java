package com.example.TechHire.controller;

import com.example.TechHire.entity.ShortlistCandidate;
import com.example.TechHire.service.ShortlistCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shortlist")
public class ShortlistCandidateController {

    @Autowired
    private ShortlistCandidateService shortlistCandidateService;

    // HR Shortlists a candidate
    @PostMapping("/{applicationId}")
    public ShortlistCandidate shortlistCandidate(@PathVariable String applicationId) {
        return shortlistCandidateService.shortlistCandidate(applicationId);
    }

    // Get all shortlisted candidates
    @GetMapping
    public List<ShortlistCandidate> getAllShortlistedCandidates() {
        return shortlistCandidateService.getAllShortlistedCandidates();
    }

    // Get shortlisted candidates by job ID
    @GetMapping("/job/{jobId}")
    public List<ShortlistCandidate> getShortlistedByJob(@PathVariable String jobId) {
        return shortlistCandidateService.getShortlistedByJob(jobId);
    }

    // Get shortlisted candidates by candidate ID
    @GetMapping("/candidate/{candidateId}")
    public List<ShortlistCandidate> getShortlistedByCandidate(@PathVariable String candidateId) {
        return shortlistCandidateService.getShortlistedByCandidate(candidateId);
    }
}
