package com.example.TechHire.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "candidate")
public class Candidate {

    @Id
    private String id;

    private String name;
    private String mail;
    private String phoneNumber;
    private String resumeUrl;
    private List<String> skills;
    private String education;
    private String experience;
    private String coverLetter;
    private String address;
    private List<String> jobApplied;

}
