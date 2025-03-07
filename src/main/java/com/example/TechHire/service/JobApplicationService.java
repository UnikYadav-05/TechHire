package com.example.TechHire.service;

import com.example.TechHire.entity.JobApplication;
import com.example.TechHire.entity.JobPosting;
import com.example.TechHire.repository.JobApplicationRepository;
import com.example.TechHire.repository.JobPostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private JobPostingRepository jobPostingRepository;

    // ✅ Apply for a job (Automatically fetch jobId, candidateId is auto-generated)
    public JobApplication applyForJob(String jobId, JobApplication jobApplication) {
        // Check if the job exists
        Optional<JobPosting> jobPosting = jobPostingRepository.findById(jobId);
        if (jobPosting.isEmpty()) {
            throw new RuntimeException("Job ID " + jobId + " not found.");
        }

        // Set the job ID in the application
        jobApplication.setJobId(jobId);

        // ✅ Save the application (MongoDB generates `candidateId`)
        return jobApplicationRepository.save(jobApplication);
    }

    // ✅ Get all job applications
    public List<JobApplication> getAllApplications() {
        return jobApplicationRepository.findAll();
    }

    // ✅ Get a specific job application by candidate ID
    public Optional<JobApplication> getApplicationById(String candidateId) {
        return jobApplicationRepository.findById(candidateId);
    }

    // ✅ Delete a job application
    public void deleteApplication(String candidateId) {
        jobApplicationRepository.deleteById(candidateId);
    }
}
