package com.example.TechHire.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "add_member") // MongoDB Collection Name
@Data
public class AddMember {

    @Id
    private String id; // Auto-generated unique ID

    private String name; // Member Name
    private String email; // Member Email
    private String designation; // Member Designation
    private String mobileNo; // Member Mobile Number
}
