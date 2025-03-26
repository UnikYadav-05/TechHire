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

    // ✅ Create a new job posting (Now linked with HR ID)
    public JobPosting createJob(String hrId, JobPosting jobPosting) {
        jobPosting.setHrId(hrId); // Assign HR ID to the job
        JobPosting savedJob = jobPostingRepository.save(jobPosting);

        // 🔔 Notify All Candidates About New Job Posting
        String message = "New job posted: " + savedJob.getTitle() + " at " + savedJob.getCompany();
        notificationService.sendNotificationToAllCandidates(message);

        return savedJob;
    }

    // ✅ Get all job postings
    public List<JobPosting> getAllJobs() {
        return jobPostingRepository.findAll();
    }

    // ✅ Get a job posting by ID
    public Optional<JobPosting> getJobById(String jobId) {
        return jobPostingRepository.findById(jobId);
    }

    // ✅ Get all jobs posted by a specific HR
    public List<JobPosting> getJobsByHR(String hrId) {
        return jobPostingRepository.findByHrId(hrId);
    }

    // ✅ Update an existing job posting
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
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    // ✅ Delete a job posting
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
