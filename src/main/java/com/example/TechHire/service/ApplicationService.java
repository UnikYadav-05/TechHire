package com.example.TechHire.service;

import com.example.TechHire.entity.Application;
import com.example.TechHire.repository.ApplicationRepository;
import com.example.TechHire.repository.CandidateRepository;
import com.example.TechHire.repository.JobPostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobPostingRepository jobPostingRepository;

    // Apply for a job (candidate applies)
    public Application applyForJob(String candidateId, String jobId) {
        if (!candidateRepository.existsById(candidateId)) {
            throw new RuntimeException("Candidate not found!");
        }
        if (!jobPostingRepository.existsById(jobId)) {
            throw new RuntimeException("Job posting not found!");
        }

        // ✅ Prevent duplicate applications
        if (applicationRepository.findByCandidateIdAndJobId(candidateId, jobId).isPresent()) {
            throw new RuntimeException("You have already applied for this job!");
        }


        Application application = new Application();
        application.setCandidateId(candidateId);
        application.setJobId(jobId);
        application.setStatus("Pending"); // Default status when applying

        return applicationRepository.save(application);
    }

    // Get all applications
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // Get applications by candidate ID
    public List<Application> getApplicationsByCandidate(String candidateId) {
        return applicationRepository.findByCandidateId(candidateId);
    }

    // Get applications by job ID
    public List<Application> getApplicationsByJob(String jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    // Get applications by status
    public List<Application> getApplicationsByStatus(String status) {
        return applicationRepository.findByStatus(status);
    }

    // Update application status (HR updates status)
    public Application updateApplicationStatus(String id, String newStatus) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found!"));

        application.setStatus(newStatus);
        return applicationRepository.save(application);
    }

    // Delete an application (if needed)
    public void deleteApplication(String id) {
        applicationRepository.deleteById(id);
    }
}
