package com.example.TechHire.service;

import com.example.TechHire.entity.CodingRound;
import com.example.TechHire.entity.OnlineAssessment;
import com.example.TechHire.repository.CodingRoundRepository;
import com.example.TechHire.repository.OnlineAssessmentRepository;
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
public class CodingRoundService {

    @Autowired
    private CodingRoundRepository codingRoundRepository;

    @Autowired
    private OnlineAssessmentRepository onlineAssessmentRepository;

    @Autowired
    private NotificationService notificationService;

    // **Method to update scores from CSV file**
    public Map<String, Object> updateCodingRoundScoresFromCsv(MultipartFile file) {
        int updatedCount = 0;
        int notFoundCount = 0;
        int missingDataCount = 0;
        int invalidFormatCount = 0;

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
                    String candidateEmail = record.get("CandidateEmail");
                    String scoreStr = record.get("Score");

                    System.out.println("Processing record: Email=" + candidateEmail + ", Score=" + scoreStr);

                    if (candidateEmail == null || candidateEmail.isEmpty() ||
                            scoreStr == null || scoreStr.isEmpty()) {
                        missingDataCount++;
                        System.out.println("Skipping record due to missing data.");
                        continue;
                    }

                    // Convert European decimal format (comma to period)
                    double score;
                    try {
                        scoreStr = scoreStr.replace(",", ".");
                        score = Double.parseDouble(scoreStr);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid score format for candidate: " + candidateEmail);
                        invalidFormatCount++;
                        continue;
                    }

                    Optional<CodingRound> codingRoundOpt = codingRoundRepository.findByCandidateEmail(candidateEmail);
                    if (codingRoundOpt.isPresent()) {
                        CodingRound codingRound = codingRoundOpt.get();
                        System.out.println("Updating score for: " + candidateEmail +
                                " | Old Score: " + codingRound.getScore() +
                                " -> New Score: " + score);
                        codingRound.setScore(score);
                        codingRoundRepository.save(codingRound);
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


    // **Method to send coding round invitation**
    public CodingRound sendCodingRound(String assessmentId, LocalDate codingTestDate, LocalTime codingTestStartTime,
                                       LocalTime codingTestEndTime, LocalTime codingTestDeadline,
                                       String codingPlatformUrl, String instructions) {
        OnlineAssessment onlineAssessment = onlineAssessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Online Assessment not found"));

        onlineAssessment.setStatus("Coding_Round");
        onlineAssessmentRepository.save(onlineAssessment);

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
                instructions,
                0.0, // Default score is set to 0
                onlineAssessment.getCandidateName(),
                onlineAssessment.getCandidateEmail(),
                onlineAssessment.getPhoneNumber()
        );

        CodingRound savedCodingRound = codingRoundRepository.save(codingRound);

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
