package com.example.TechHire.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hr") // Collection name in MongoDB
@Data
public class HR {
    @Id
    private String id;  // Primary key
    private String name;
    private String email;
    private String phoneNumber;
    private String experience; // Years of experience

    public HR(String name, String email, String phoneNumber, String experience) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.experience = experience;
    }
}
