package com.example.TechHire.service;

import com.example.TechHire.entity.Candidate;
import com.example.TechHire.entity.AddInterview;
import com.example.TechHire.entity.JobPosting;
import com.example.TechHire.repository.CandidateRepository;
import com.example.TechHire.repository.AddInterviewRepository;
import com.example.TechHire.repository.JobPostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AddInterviewService {

    @Autowired
    private AddInterviewRepository interviewRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private NotificationService notificationService;

    // Schedule an interview
    public AddInterview scheduleInterview(String candidateId, String jobId, AddInterview interview) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
        JobPosting job = jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        interview.setCandidateId(candidate.getId());
        interview.setName(candidate.getName());
        interview.setJobId(job.getId());
        interview.setCreatedAt(Instant.now());
        interview.setUpdatedAt(Instant.now());

        AddInterview savedInterview = interviewRepository.save(interview);

        // ✅ Notify candidate
        notificationService.sendNotification(
                candidate.getId(),
                "Your interview for the position of " + job.getTitle() + " has been scheduled on " +
                        interview.getDateInterview() + " at " + interview.getTimeInterview() + "."
        );

        // ✅ Notify assigned manager (if assigned)
        if (interview.getAssignedManager() != null) {
            notificationService.sendNotification(
                    interview.getAssignedManager(),
                    "You have been assigned to interview " + candidate.getName() + " for the position of " +
                            job.getTitle() + " on " + interview.getDateInterview() + "."
            );
        }

        return savedInterview;
    }

    // Get all interviews
    public List<AddInterview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    // Get interviews by candidate ID
    public List<AddInterview> getInterviewsByCandidate(String candidateId) {
        return interviewRepository.findByCandidateId(candidateId);
    }

    // Get interviews by job ID
    public List<AddInterview> getInterviewsByJob(String jobId) {
        return interviewRepository.findByJobId(jobId);
    }

    // Get interviews by status
    public List<AddInterview> getInterviewsByStatus(String status) {
        return interviewRepository.findByStatus(status);
    }

    // Update interview details
    public AddInterview updateInterview(String id, AddInterview updatedInterview) {
        AddInterview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        interview.setInterviewMode(updatedInterview.getInterviewMode());
        interview.setDateInterview(updatedInterview.getDateInterview());
        interview.setTimeInterview(updatedInterview.getTimeInterview());
        interview.setAssignedManager(updatedInterview.getAssignedManager());
        interview.setMeetingLink(updatedInterview.getMeetingLink());
        interview.setInterviewPanelMembers(updatedInterview.getInterviewPanelMembers());
        interview.setStatus(updatedInterview.getStatus());
        interview.setUpdatedAt(Instant.now());

        AddInterview updated = interviewRepository.save(interview);

        // ✅ Notify candidate about reschedule
        notificationService.sendNotification(
                interview.getCandidateId(),
                "Your interview has been updated to " + updated.getDateInterview() +
                        " at " + updated.getTimeInterview() + "."
        );

        // ✅ Notify assigned manager about reschedule
        if (updated.getAssignedManager() != null) {
            notificationService.sendNotification(
                    updated.getAssignedManager(),
                    "The interview for candidate " + updated.getName() +
                            " has been updated to " + updated.getDateInterview() + "."
            );
        }

        return updated;
    }

    // Delete an interview
    public void deleteInterview(String id) {
        AddInterview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        interviewRepository.deleteById(id);

        // ✅ Notify candidate about cancellation
        notificationService.sendNotification(
                interview.getCandidateId(),
                "Your interview has been canceled."
        );

        // ✅ Notify assigned manager about cancellation
        if (interview.getAssignedManager() != null) {
            notificationService.sendNotification(
                    interview.getAssignedManager(),
                    "The interview for candidate " + interview.getName() + " has been canceled."
            );
        }
    }
}