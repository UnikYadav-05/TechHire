package com.example.TechHire.controller;

import com.example.TechHire.entity.OnlineAssessment;
import com.example.TechHire.service.OnlineAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/assessment")
public class OnlineAssessmentController {

    @Autowired
    private OnlineAssessmentService onlineAssessmentService;

    @PostMapping("/{shortlistId}")
    public ResponseEntity<OnlineAssessment> sendOnlineAssessment(
            @PathVariable String shortlistId,
            @RequestBody OnlineAssessment request) {

        OnlineAssessment assessment = onlineAssessmentService.sendOnlineAssessment(
                shortlistId, request.getTestDate(), request.getTestDeadline(), request.getAttachments() , request.getType_of_test()
        );

        return ResponseEntity.ok(assessment);
    }

    @PostMapping("/upload-scores")
    public ResponseEntity<?> uploadCsvAndUpdateScores(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(onlineAssessmentService.updateScoresFromCsv(file));
    }

    @GetMapping
    public ResponseEntity<?> getAllAssessments() {
        return ResponseEntity.ok(onlineAssessmentService.getAllAssessments());
    }
}
