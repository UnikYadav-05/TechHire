package com.example.TechHire.controller;

import com.example.TechHire.entity.JobPosting;
import com.example.TechHire.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs_posting")
public class JobPostingController {

    @Autowired
    private JobPostingService jobPostingService;

    // ✅ Create a new job posting (HR must provide hrId)
    @PostMapping
    public ResponseEntity<JobPosting> createJob(@RequestParam String hrId, @RequestBody JobPosting jobPosting) {
        JobPosting savedJob = jobPostingService.createJob(hrId, jobPosting);
        return ResponseEntity.ok(savedJob);
    }

    // ✅ Get all job postings
    @GetMapping
    public ResponseEntity<List<JobPosting>> getAllJobs() {
        return ResponseEntity.ok(jobPostingService.getAllJobs());
    }

    // ✅ Get a job by ID
    @GetMapping("/{jobId}")
    public ResponseEntity<Optional<JobPosting>> getJobById(@PathVariable String jobId) {
        return ResponseEntity.ok(jobPostingService.getJobById(jobId));
    }

    // ✅ Get all jobs posted by a specific HR
    @GetMapping("/by-hr/{hrId}")
    public ResponseEntity<List<JobPosting>> getJobsByHR(@PathVariable String hrId) {
        return ResponseEntity.ok(jobPostingService.getJobsByHR(hrId));
    }

    // ✅ Update a job posting
    @PutMapping("/{jobId}")
    public ResponseEntity<JobPosting> updateJob(@PathVariable String jobId, @RequestBody JobPosting jobDetails) {
        JobPosting updatedJob = jobPostingService.updateJob(jobId, jobDetails);
        return ResponseEntity.ok(updatedJob);
    }

    // ✅ Delete a job posting
    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> deleteJob(@PathVariable String jobId) {
        jobPostingService.deleteJob(jobId);
        return ResponseEntity.ok("Job deleted successfully.");
    }
}
