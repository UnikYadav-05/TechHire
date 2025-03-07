package com.example.TechHire.controller;

import com.example.TechHire.entity.JobApplication;
import com.example.TechHire.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/job_applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    // ✅ Apply for a job (Job ID is fetched automatically)
    @PostMapping("/{jobId}") // Pass jobId in the URL
    public JobApplication applyForJob(@PathVariable String jobId, @RequestBody JobApplication jobApplication) {
        return jobApplicationService.applyForJob(jobId, jobApplication);
    }

    // ✅ Get all job applications
    @GetMapping
    public List<JobApplication> getAllApplications() {
        return jobApplicationService.getAllApplications();
    }

    // ✅ Get a specific job application
    @GetMapping("/{candidateId}")
    public Optional<JobApplication> getApplicationById(@PathVariable String candidateId) {
        return jobApplicationService.getApplicationById(candidateId);
    }

    // ✅ Delete a job application
    @DeleteMapping("/{candidateId}")
    public void deleteApplication(@PathVariable String candidateId) {
        jobApplicationService.deleteApplication(candidateId);
    }
}
