package com.example.TechHire.service;

import com.example.TechHire.entity.Candidate;
import com.example.TechHire.entity.JobApplication;
import com.example.TechHire.entity.JobPosting;
import com.example.TechHire.repository.CandidateRepository;
import com.example.TechHire.repository.JobApplicationRepository;
import com.example.TechHire.repository.JobPostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobPostingRepository jobPostingRepository;

    // Apply for a job (fetches jobId & candidateId automatically)
    public JobApplication applyForJob(String jobId, String candidateId, JobApplication jobApplication) {
        JobPosting job = jobPostingRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() -> new RuntimeException("Candidate not found"));

        jobApplication.setJobId(job.getJobId());
        jobApplication.setCandidateId(candidate.getId());

        return jobApplicationRepository.save(jobApplication);
    }

    // Get all job applications
    public List<JobApplication> getAllApplications() {
        return jobApplicationRepository.findAll();
    }

    // Get applications by candidate ID
    public List<JobApplication> getApplicationsByCandidate(String candidateId) {
        return jobApplicationRepository.findByCandidateId(candidateId);
    }

    // Get applications by job ID
    public List<JobApplication> getApplicationsByJob(String jobId) {
        return jobApplicationRepository.findByJobId(jobId);
    }

    // Delete job application
    public void deleteApplication(String jobId, String candidateId) {
        List<JobApplication> applications = jobApplicationRepository.findByCandidateId(candidateId);
        applications.stream()
                .filter(app -> app.getJobId().equals(jobId))
                .findFirst()
                .ifPresent(jobApplicationRepository::delete);
    }
}
