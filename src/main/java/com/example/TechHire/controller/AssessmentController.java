package com.example.TechHire.controller;

import com.example.TechHire.entity.Assessment;
import com.example.TechHire.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessments")
@CrossOrigin("*")
public class AssessmentController {

    @Autowired
    private AssessmentService service;

    @GetMapping
    public List<Assessment> getAllAssessments() {
        return service.getAllAssessments();
    }

    @GetMapping("/{id}")
    public Assessment getAssessmentById(@PathVariable String id) {
        return service.getAssessmentById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found with id: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Assessment createAssessment(@RequestBody Assessment assessment) {
        return service.createAssessment(assessment);
    }

    @PutMapping("/{id}")
    public Assessment updateAssessment(@PathVariable String id, @RequestBody Assessment assessment) {
        return service.updateAssessment(id, assessment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssessment(@PathVariable String id) {
        service.deleteAssessment(id);
    }
}
