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

    public OnlineAssessment sendOnlineAssessment(String shortlistId, LocalDate testDate,
                                                 LocalTime testDeadline, String attachments , String type_of_test) {

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

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                String candidateEmail = record.get("Candidate Email").trim();
                String scoreStr = record.get("Score").trim();

                System.out.println("Processing record: Email=" + candidateEmail + ", Score=" + scoreStr); // 🔹 Print to check values

                if (candidateEmail.isEmpty() || scoreStr.isEmpty()) {
                    missingDataCount++;
                    System.out.println("Skipping record due to missing data.");
                    continue;
                }

                try {
                    double score = Double.parseDouble(scoreStr);
                    Optional<OnlineAssessment> assessmentOpt = onlineAssessmentRepository.findByCandidateEmail(candidateEmail);

                    if (assessmentOpt.isPresent()) {
                        OnlineAssessment assessment = assessmentOpt.get();
                        System.out.println("Updating score for: " + candidateEmail + " | Old Score: " + assessment.getScore() + " -> New Score: " + score);

                        assessment.setScore(score);  // ✅ Update the score properly
                        onlineAssessmentRepository.save(assessment);  // ✅ Save updated assessment

                        System.out.println("Successfully updated score for: " + candidateEmail);
                        updatedCount++;
                    } else {
                        System.out.println("Candidate email not found in database: " + candidateEmail);
                        notFoundCount++;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid score format for candidate: " + candidateEmail);
                    missingDataCount++;
                }
            }

        } catch (IOException e) {
            return Map.of("error", "Error processing CSV file: " + e.getMessage());
        }

        System.out.println("Score update process completed. Updated: " + updatedCount + ", Not Found: " + notFoundCount + ", Missing Data: " + missingDataCount);

        return Map.of(
                "message", "Score update completed",
                "updatedCount", updatedCount,
                "notFoundCount", notFoundCount,
                "missingDataCount", missingDataCount
        );
    }


    public List<OnlineAssessment> getAllAssessments() {
        return onlineAssessmentRepository.findAll();
    }
}
