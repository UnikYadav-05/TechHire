package com.example.TechHire.controller;

import com.example.TechHire.entity.AddInterview;
import com.example.TechHire.service.AddInterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addInterviews")
public class AddInterviewController {

    @Autowired
    private AddInterviewService interviewService;

    // Schedule an interview
    @PostMapping("/{candidateId}/{jobId}")
    public AddInterview scheduleInterview(
            @PathVariable String candidateId,
            @PathVariable String jobId,
            @RequestBody AddInterview interview) {
        return interviewService.scheduleInterview(candidateId, jobId, interview);
    }

    // Get all interviews
    @GetMapping
    public List<AddInterview> getAllInterviews() {
        return interviewService.getAllInterviews();
    }

    // Get interviews by candidate ID
    @GetMapping("/candidate/{candidateId}")
    public List<AddInterview> getInterviewsByCandidate(@PathVariable String candidateId) {
        return interviewService.getInterviewsByCandidate(candidateId);
    }

    // Get interviews by job ID
    @GetMapping("/job/{jobId}")
    public List<AddInterview> getInterviewsByJob(@PathVariable String jobId) {
        return interviewService.getInterviewsByJob(jobId);
    }

    // Get interviews by status
    @GetMapping("/status/{status}")
    public List<AddInterview> getInterviewsByStatus(@PathVariable String status) {
        return interviewService.getInterviewsByStatus(status);
    }

    // Update interview
    @PutMapping("/{id}")
    public AddInterview updateInterview(@PathVariable String id, @RequestBody AddInterview updatedInterview) {
        return interviewService.updateInterview(id, updatedInterview);
    }

    // Delete an interview
    @DeleteMapping("/{id}")
    public void deleteInterview(@PathVariable String id) {
        interviewService.deleteInterview(id);
    }
}
