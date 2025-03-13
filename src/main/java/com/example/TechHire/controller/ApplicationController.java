package com.example.TechHire.controller;

import com.example.TechHire.entity.Application;
import com.example.TechHire.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    // Apply for a job
    @PostMapping("/{candidateId}/{jobId}")
    public Application applyForJob(
            @PathVariable String candidateId,
            @PathVariable String jobId) {
        return applicationService.applyForJob(candidateId, jobId);
    }

    // Get all applications
    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    // Get applications by candidate ID
    @GetMapping("/candidate/{candidateId}")
    public List<Application> getApplicationsByCandidate(@PathVariable String candidateId) {
        return applicationService.getApplicationsByCandidate(candidateId);
    }

    // Get applications by job ID
    @GetMapping("/job/{jobId}")
    public List<Application> getApplicationsByJob(@PathVariable String jobId) {
        return applicationService.getApplicationsByJob(jobId);
    }

    // Get applications by status
    @GetMapping("/status/{status}")
    public List<Application> getApplicationsByStatus(@PathVariable String status) {
        return applicationService.getApplicationsByStatus(status);
    }

    // Update application status (HR updates the status)
    @PutMapping("/{id}/status")
    public Application updateApplicationStatus(@PathVariable String id, @RequestParam String status) {
        return applicationService.updateApplicationStatus(id, status);
    }

    // Delete an application
    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable String id) {
        applicationService.deleteApplication(id);
    }
}
