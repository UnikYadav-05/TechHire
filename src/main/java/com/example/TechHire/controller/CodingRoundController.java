package com.example.TechHire.controller;

import com.example.TechHire.entity.CodingRound;
import com.example.TechHire.service.CodingRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/codingRound")
@CrossOrigin(origins = "*")
public class CodingRoundController {

    @Autowired
    private CodingRoundService codingRoundService;

    // **Send Coding Round**
    @PostMapping("/{assessmentId}")
    public ResponseEntity<CodingRound> sendCodingRound(
            @PathVariable String assessmentId,
            @RequestBody CodingRound codingRound) {

        CodingRound savedCodingRound = codingRoundService.sendCodingRound(
                assessmentId,
                codingRound.getCodingTestDate(),
                codingRound.getCodingTestStartTime(),
                codingRound.getCodingTestEndTime(),
                codingRound.getCodingTestDeadline(),
                codingRound.getCodingPlatformUrl(),
                codingRound.getInstructions()
        );

        return ResponseEntity.ok(savedCodingRound);
    }

    // **Upload CSV to update scores**
    @PostMapping("/uploadCsv")
    public ResponseEntity<Map<String, Object>> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = codingRoundService.updateCodingRoundScoresFromCsv(file);
        return ResponseEntity.ok(response);
    }

    // **Get all coding rounds**
    @GetMapping
    public ResponseEntity<List<CodingRound>> getAllCodingRounds() {
        return ResponseEntity.ok(codingRoundService.getAllCodingRounds());
    }

    // **Get coding rounds by job**
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<CodingRound>> getCodingRoundsByJob(@PathVariable String jobId) {
        return ResponseEntity.ok(codingRoundService.getCodingRoundsByJob(jobId));
    }

    // **Get coding rounds by candidate**
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<CodingRound>> getCodingRoundsByCandidate(@PathVariable String candidateId) {
        return ResponseEntity.ok(codingRoundService.getCodingRoundsByCandidate(candidateId));
    }
}
