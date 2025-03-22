package com.example.TechHire.service;

import com.example.TechHire.entity.CodingRound;
import com.example.TechHire.entity.OnlineAssessment;
import com.example.TechHire.repository.CodingRoundRepository;
import com.example.TechHire.repository.OnlineAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CodingRoundService {

    @Autowired
    private CodingRoundRepository codingRoundRepository;

    @Autowired
    private OnlineAssessmentRepository onlineAssessmentRepository;

    @Autowired
    private NotificationService notificationService;

    // HR Sends Coding Round to a Candidate
    public CodingRound sendCodingRound(String assessmentId, LocalDate codingTestDate, LocalTime codingTestStartTime,
                                       LocalTime codingTestEndTime, LocalTime codingTestDeadline,
                                       String codingPlatformUrl, String instructions) {
        OnlineAssessment onlineAssessment = onlineAssessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Online Assessment not found"));

        CodingRound codingRound = new CodingRound(
                onlineAssessment.getCandidateId(),
                onlineAssessment.getJobId(),
                onlineAssessment.getJobAppliedId(),
                onlineAssessment.getAppliedFor(),
                codingTestDate,
                codingTestStartTime,
                codingTestEndTime,
                codingTestDeadline,
                codingPlatformUrl,
                instructions
        );

        CodingRound savedCodingRound = codingRoundRepository.save(codingRound);

        // 🔔 Send Notification for Coding Round
        String message = "You have been invited to a Coding Round for " + onlineAssessment.getAppliedFor() +
                ". Test Date: " + codingTestDate + ", Time: " + codingTestStartTime + " - " + codingTestEndTime +
                ". Access the test here: " + codingPlatformUrl;
        notificationService.sendNotification(onlineAssessment.getCandidateId(), message);

        return savedCodingRound;
    }

    // Get all coding rounds
    public List<CodingRound> getAllCodingRounds() {
        return codingRoundRepository.findAll();
    }

    // Get coding rounds by job
    public List<CodingRound> getCodingRoundsByJob(String jobId) {
        return codingRoundRepository.findByJobId(jobId);
    }

    // Get coding rounds by candidate
    public List<CodingRound> getCodingRoundsByCandidate(String candidateId) {
        return codingRoundRepository.findByCandidateId(candidateId);
    }
}
