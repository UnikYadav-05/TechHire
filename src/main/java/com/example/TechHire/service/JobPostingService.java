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

    @Autowired
    private NotificationService notificationService;

    // Create a new job posting
    public JobPosting createJob(JobPosting jobPosting) {
        JobPosting savedJob = jobPostingRepository.save(jobPosting);

        // 🔔 Notify All Candidates About New Job Posting
        String message = "New job posted: " + savedJob.getTitle() + " at " + savedJob.getCompany();
        notificationService.sendNotificationToAllCandidates(message);

        return savedJob;
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
                    JobPosting updatedJob = jobPostingRepository.save(job);

                    // 🔔 Notify All Candidates About Job Update
                    String message = "Job updated: " + updatedJob.getTitle() + " at " + updatedJob.getCompany();
                    notificationService.sendNotificationToAllCandidates(message);

                    return updatedJob;
                })
                .orElse(null);
    }

    // Delete a job posting
    public void deleteJob(String jobId) {
        Optional<JobPosting> jobPosting = jobPostingRepository.findById(jobId);
        jobPosting.ifPresent(job -> {
            jobPostingRepository.deleteById(jobId);

            // 🔔 Notify All Candidates About Job Deletion
            String message = "Job removed: " + job.getTitle() + " at " + job.getCompany();
            notificationService.sendNotificationToAllCandidates(message);
        });
    }
}

