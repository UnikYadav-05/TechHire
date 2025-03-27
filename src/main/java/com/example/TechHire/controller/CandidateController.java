package com.example.TechHire.controller;

import com.example.TechHire.entity.Candidate;
import com.example.TechHire.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    public Candidate createCandidate(@RequestBody Candidate candidate) {
        return candidateService.createCandidate(candidate);
    }

    @GetMapping
    public List<Candidate> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @GetMapping("/{id}")
    public Optional<Candidate> getCandidateById(@PathVariable String id) {
        return candidateService.getCandidateById(id);
    }

    @GetMapping("/email/{email}")
    public Optional<Candidate> getCandidateByEmail(@PathVariable String email) {
        return candidateService.getCandidateByEmail(email);
    }

    @PutMapping("/{id}")
    public Candidate updateCandidate(@PathVariable String id, @RequestBody Candidate candidateDetails) {
        return candidateService.updateCandidate(id, candidateDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteCandidate(@PathVariable String id) {
        candidateService.deleteCandidate(id);
    }
}
