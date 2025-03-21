package com.example.TechHire.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "job_postings")
@Data
@NoArgsConstructor
public class JobPosting {

    @Id
    private String id;
    private String jobId;
    private String title;
    private String company;
    private String location;
    private String jobType;
    private String description;
    private String applicationDeadline;
    private double salary;
    private List<String> skillsRequired;
}
