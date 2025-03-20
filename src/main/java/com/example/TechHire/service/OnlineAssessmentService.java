package com.example.TechHire.service;

import com.example.TechHire.entity.OnlineAssessment;
import com.example.TechHire.entity.ShortlistCandidate;
import com.example.TechHire.repository.OnlineAssessmentRepository;
import com.example.TechHire.repository.ShortlistCandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class OnlineAssessmentService {

    @Autowired
    private OnlineAssessmentRepository onlineAssessmentRepository;

    @Autowired
    private ShortlistCandidateRepository shortlistCandidateRepository;

    public OnlineAssessment sendOnlineAssessment(String shortlistId, LocalDate testDate,
                                                 LocalTime testStartTime, LocalTime testEndTime,
                                                 LocalTime testDeadline) {

        ShortlistCandidate shortlistCandidate = shortlistCandidateRepository.findById(shortlistId)
                .orElseThrow(() -> new RuntimeException("Shortlisted Candidate not found"));

        OnlineAssessment assessment = new OnlineAssessment(
                shortlistCandidate.getCandidateId(),
                shortlistCandidate.getJobId(),
                shortlistCandidate.getApplicationId(),
                testDate, testStartTime, testEndTime, testDeadline
        );

        return onlineAssessmentRepository.save(assessment);
    }

    public List<OnlineAssessment> getAllAssessments() {
        return onlineAssessmentRepository.findAll();
    }
}
