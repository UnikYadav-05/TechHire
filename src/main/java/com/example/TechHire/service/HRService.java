package com.example.TechHire.service;

import com.example.TechHire.entity.HR;
import com.example.TechHire.repository.HRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HRService {

    @Autowired
    private HRRepository hrRepository;

    public HR saveHR(HR hr) {
        // Check if email already exists to prevent duplicates
        Optional<HR> existingHR = hrRepository.findByEmail(hr.getEmail());
        if (existingHR.isPresent()) {
            throw new RuntimeException("HR with this email already exists!");
        }
        return hrRepository.save(hr);
    }

    public List<HR> getAllHRs() {
        return hrRepository.findAll();
    }

    public Optional<HR> getHRById(String id) {
        return hrRepository.findById(id);
    }
}
