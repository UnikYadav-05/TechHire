package com.example.TechHire.controller;

import com.example.TechHire.entity.OnlineAssessment;
import com.example.TechHire.service.OnlineAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                shortlistId, request.getTestDate(), request.getTestStartTime(),
                request.getTestEndTime(), request.getTestDeadline()
        );

        return ResponseEntity.ok(assessment);
    }

    @GetMapping
    public ResponseEntity<?> getAllAssessments() {
        return ResponseEntity.ok(onlineAssessmentService.getAllAssessments());
    }
}
