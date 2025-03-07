package com.example.TechHire.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "assessments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assessment {
    @Id
    private String id;
    private String title;
    private String about;
    private String createdBy;
    private Date creationDate;
    private Date deadline;
    private String assignTo;
    private String status;
    private String attachments;
}

