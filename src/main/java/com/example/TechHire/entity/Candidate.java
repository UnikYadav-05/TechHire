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
    private String email;
    private String phoneNumber;
    private String address;
    private List<String> skills;
    private String education;
    private String experience;
    private String linkedin;
    private String github;
    private String codingProfile;
    private String resumeUrl;
}
