package com.example.TechHire.controller;

import com.example.TechHire.entity.HR;
import com.example.TechHire.service.HRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hr") // Base URL: http://localhost:8081/api/hr
public class HRController {

    @Autowired
    private HRService hrService;

    // ✅ Save HR data
    @PostMapping("/save")
    public ResponseEntity<HR> saveHR(@RequestBody HR hr) {
        HR savedHR = hrService.saveHR(hr);
        return ResponseEntity.ok(savedHR);
    }

    // ✅ Get all HRs
    @GetMapping("/all")
    public ResponseEntity<List<HR>> getAllHRs() {
        return ResponseEntity.ok(hrService.getAllHRs());
    }

    // ✅ Get HR by ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<HR>> getHRById(@PathVariable String id) {
        return ResponseEntity.ok(hrService.getHRById(id));
    }
}
