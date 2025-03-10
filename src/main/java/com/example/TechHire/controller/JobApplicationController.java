package com.example.TechHire.controller;

import com.example.TechHire.entity.JobApplication;
import com.example.TechHire.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job_applied")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    // Apply for a job
    @PostMapping("/{jobId}/{candidateId}")
    public JobApplication applyForJob(
            @PathVariable String jobId,
            @PathVariable String candidateId,
            @RequestBody JobApplication jobApplication) {
        return jobApplicationService.applyForJob(jobId, candidateId, jobApplication);
    }

    // Get all job applications
    @GetMapping
    public List<JobApplication> getAllApplications() {
        return jobApplicationService.getAllApplications();
    }

    // Get applications by candidate ID
    @GetMapping("/candidate/{candidateId}")
    public List<JobApplication> getApplicationsByCandidate(@PathVariable String candidateId) {
        return jobApplicationService.getApplicationsByCandidate(candidateId);
    }

    // Get applications by job ID
    @GetMapping("/job/{jobId}")
    public List<JobApplication> getApplicationsByJob(@PathVariable String jobId) {
        return jobApplicationService.getApplicationsByJob(jobId);
    }

    // Delete job application
    @DeleteMapping("/{jobId}/{candidateId}")
    public void deleteApplication(@PathVariable String jobId, @PathVariable String candidateId) {
        jobApplicationService.deleteApplication(jobId, candidateId);
    }
}
