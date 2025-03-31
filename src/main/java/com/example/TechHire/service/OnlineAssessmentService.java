package com.example.TechHire.service;

import com.example.TechHire.entity.OnlineAssessment;
import com.example.TechHire.entity.ShortlistCandidate;
import com.example.TechHire.repository.OnlineAssessmentRepository;
import com.example.TechHire.repository.ShortlistCandidateRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OnlineAssessmentService {

    @Autowired
    private OnlineAssessmentRepository onlineAssessmentRepository;

    @Autowired
    private ShortlistCandidateRepository shortlistCandidateRepository;

    @Autowired
    private NotificationService notificationService;

    public OnlineAssessment sendOnlineAssessment(String shortlistId, Date testDate,
                                                 Date testDeadline, String attachments , String type_of_test) {

        ShortlistCandidate shortlistCandidate = shortlistCandidateRepository.findById(shortlistId)
                .orElseThrow(() -> new RuntimeException("Shortlisted Candidate not found"));

        shortlistCandidate.setStatus("Pending");
        shortlistCandidateRepository.save(shortlistCandidate);

        OnlineAssessment assessment = new OnlineAssessment(
                shortlistCandidate.getCandidateId(),
                shortlistCandidate.getJobId(),
                shortlistCandidate.getJobAppliedId(),
                shortlistCandidate.getAppliedFor(),
                testDate, testDeadline, attachments , type_of_test,
                shortlistCandidate.getCandidateName(),
                shortlistCandidate.getCandidateEmail(),
                shortlistCandidate.getPhoneNumber()
        );


        OnlineAssessment savedAssessment = onlineAssessmentRepository.save(assessment);

        // 🔔 Send Notification for Online Assessment
        String message = "You have received an Online Assessment for " + shortlistCandidate.getAppliedFor() +
                ". Test Date: " + testDate;
        notificationService.sendNotification(shortlistCandidate.getCandidateId(), message);

        return savedAssessment;
    }

    public Map<String, Object> updateScoresFromCsv(MultipartFile file) {
        int updatedCount = 0;
        int notFoundCount = 0;
        int missingDataCount = 0;
        int invalidFormatCount = 0;
        // Create CSV format with semicolon delimiter and proper header handling
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setDelimiter(';')
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, csvFormat)) {
            for (CSVRecord record : csvParser) {
                try {
                    // Get values with case-insensitive header names
                    String candidateEmail = record.get("CandidateEmail");
                    String scoreStr = record.get("Score");
                    System.out.println("Processing record: Email=" + candidateEmail + ", Score=" + scoreStr);
                    if (candidateEmail == null || candidateEmail.isEmpty() ||
                            scoreStr == null || scoreStr.isEmpty()) {
                        missingDataCount++;
                        System.out.println("Skipping record due to missing data.");
                        continue;
                    }
                    // Handle score format (replace comma with period for decimal)
                    double score;
                    try {
                        scoreStr = scoreStr.replace(",", "."); // Convert European decimal format
                        score = Double.parseDouble(scoreStr);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid score format for candidate: " + candidateEmail);
                        invalidFormatCount++;
                        continue;
                    }
                    Optional<OnlineAssessment> assessmentOpt = onlineAssessmentRepository.findByCandidateEmail(candidateEmail);
                    if (assessmentOpt.isPresent()) {
                        OnlineAssessment assessment = assessmentOpt.get();
                        System.out.println("Updating score for: " + candidateEmail +
                                " | Old Score: " + assessment.getScore() +
                                " -> New Score: " + score);
                        assessment.setScore(score);
                        onlineAssessmentRepository.save(assessment);
                        updatedCount++;
                    } else {
                        System.out.println("Candidate email not found in database: " + candidateEmail);
                        notFoundCount++;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error processing record: " + e.getMessage());
                    invalidFormatCount++;
                }
            }
        } catch (IOException e) {
            return Map.of("error", "Error processing CSV file: " + e.getMessage());
        }
        System.out.println("Score update process completed. " +
                "Updated: " + updatedCount +
                ", Not Found: " + notFoundCount +
                ", Missing Data: " + missingDataCount +
                ", Invalid Format: " + invalidFormatCount);
        return Map.of(
                "message", "Score update completed",
                "updatedCount", updatedCount,
                "notFoundCount", notFoundCount,
                "missingDataCount", missingDataCount,
                "invalidFormatCount", invalidFormatCount
        );
    }


    public List<OnlineAssessment> getAllAssessments() {
        return onlineAssessmentRepository.findAll();
    }
}
