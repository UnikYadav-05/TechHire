package com.example.TechHire.controller;

import com.example.TechHire.entity.CodingRound;
import com.example.TechHire.service.CodingRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/codingRound")
@CrossOrigin(origins = "*")  // Allow requests from frontend (React, Angular, etc.)
public class CodingRoundController {

    @Autowired
    private CodingRoundService codingRoundService;


    @PostMapping("/{assessmentId}")
    public CodingRound sendCodingRound(
            @PathVariable String assessmentId,
            @RequestBody CodingRound codingRound) {

        return codingRoundService.sendCodingRound(
                assessmentId,
                codingRound.getCodingTestDate(),
                codingRound.getCodingTestStartTime(),
                codingRound.getCodingTestEndTime(),
                codingRound.getCodingTestDeadline(),
                codingRound.getCodingPlatformUrl(),
                codingRound.getInstructions()
        );
    }

    @GetMapping
    public List<CodingRound> getAllCodingRounds() {
        return codingRoundService.getAllCodingRounds();
    }


    @GetMapping("/job/{jobId}")
    public List<CodingRound> getCodingRoundsByJob(@PathVariable String jobId) {
        return codingRoundService.getCodingRoundsByJob(jobId);
    }


    @GetMapping("/candidate/{candidateId}")
    public List<CodingRound> getCodingRoundsByCandidate(@PathVariable String candidateId) {
        return codingRoundService.getCodingRoundsByCandidate(candidateId);
    }
}
