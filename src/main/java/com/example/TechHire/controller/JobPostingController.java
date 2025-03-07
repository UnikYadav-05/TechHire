package com.example.TechHire.controller;

import com.example.TechHire.entity.JobPosting;
import com.example.TechHire.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs_posting")
public class JobPostingController {

    @Autowired
    private JobPostingService jobPostingService;

    // Create a new job posting
    @PostMapping
    public JobPosting createJob(@RequestBody JobPosting jobPosting) {
        return jobPostingService.createJob(jobPosting);
    }

    // Get all job postings
    @GetMapping
    public List<JobPosting> getAllJobs() {
        return jobPostingService.getAllJobs();
    }

    // Get a job by ID
    @GetMapping("/{jobId}")
    public Optional<JobPosting> getJobById(@PathVariable String jobId) {
        return jobPostingService.getJobById(jobId);
    }

    // Update a job posting
    @PutMapping("/{jobId}")
    public JobPosting updateJob(@PathVariable String jobId, @RequestBody JobPosting jobDetails) {
        return jobPostingService.updateJob(jobId, jobDetails);
    }

    // Delete a job posting
    @DeleteMapping("/{jobId}")
    public void deleteJob(@PathVariable String jobId) {
        jobPostingService.deleteJob(jobId);
    }
}

