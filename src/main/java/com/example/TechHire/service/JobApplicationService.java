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
import java.util.Optional;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private NotificationService notificationService;

    // Apply for a job (fetches jobId & candidateId automatically)
    public JobApplication applyForJob(String jobId, String candidateId) {
        JobPosting job = jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        // ✅ Check if the candidate has already applied for this job
        Optional<JobApplication> existingApplication = jobApplicationRepository.findByJobIdAndCandidateId(jobId, candidateId);
        if (existingApplication.isPresent()) {
            throw new IllegalStateException("Already applied for this job.");
        }

        // Create and save the new job application
        JobApplication jobApplication = new JobApplication();
        jobApplication.setJobId(job.getId());
        jobApplication.setCandidateId(candidate.getId());
        jobApplication.setAppliedFor(job.getTitle());
        jobApplication.setCompany(job.getCompany());
        jobApplication.setLocation(job.getLocation());
        jobApplication.setSalary(String.valueOf(job.getSalary())); // Convert to String
        jobApplication.setHrId(job.getHrId()); // Fetch HR ID from JobPosting
        jobApplication.setName(candidate.getName());
        jobApplication.setEmail(candidate.getEmail());
        jobApplication.setResumeUrl(candidate.getResumeUrl());
        jobApplication.setAddress(candidate.getAddress());
        jobApplication.setPhoneNumber(candidate.getPhoneNumber());
        jobApplication.setStatus("Pending");

        JobApplication savedApplication = jobApplicationRepository.save(jobApplication);

        // 🔔 Send Notification to Candidate
        String message = "You have successfully applied for the job: " + job.getTitle();
        notificationService.sendNotification(candidate.getId(), message);

        return savedApplication;
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

    public List<JobApplication> getApplicationsByHr(String hrId) {
        return jobApplicationRepository.findByHrId(hrId);
    }

    // Delete job application
    public void deleteApplication(String jobId, String candidateId) {
        List<JobApplication> applications = jobApplicationRepository.findByCandidateId(candidateId);
        applications.stream()
                .filter(app -> app.getJobId().equals(jobId))
                .findFirst()
                .ifPresent(jobApplication -> {
                    jobApplicationRepository.delete(jobApplication);

                    // 🔔 Send Notification to Candidate
                    String message = "Your application for the job: " + jobId + " has been withdrawn.";
                    notificationService.sendNotification(candidateId, message);
                });
    }
}
