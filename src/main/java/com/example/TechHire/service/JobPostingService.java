package com.example.TechHire.service;

import com.example.TechHire.entity.JobPosting;
import com.example.TechHire.repository.JobPostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobPostingService {

    @Autowired
    private JobPostingRepository jobPostingRepository;

    // Create a new job posting
    public JobPosting createJob(JobPosting jobPosting) {
        return jobPostingRepository.save(jobPosting);
    }

    // Get all job postings
    public List<JobPosting> getAllJobs() {
        return jobPostingRepository.findAll();
    }

    // Get a job posting by ID
    public Optional<JobPosting> getJobById(String jobId) {
        return jobPostingRepository.findById(jobId);
    }

    // Update an existing job posting
    public JobPosting updateJob(String jobId, JobPosting jobDetails) {
        return jobPostingRepository.findById(jobId)
                .map(job -> {
                    job.setTitle(jobDetails.getTitle());
                    job.setCompany(jobDetails.getCompany());
                    job.setLocation(jobDetails.getLocation());
                    job.setJobType(jobDetails.getJobType());
                    job.setDescription(jobDetails.getDescription());
                    job.setApplicationDeadline(jobDetails.getApplicationDeadline());
                    job.setSalary(jobDetails.getSalary());
                    job.setSkillsRequired(jobDetails.getSkillsRequired());
                    return jobPostingRepository.save(job);
                })
                .orElse(null);
    }

    // Delete a job posting
    public void deleteJob(String jobId) {
        jobPostingRepository.deleteById(jobId);
    }
}

