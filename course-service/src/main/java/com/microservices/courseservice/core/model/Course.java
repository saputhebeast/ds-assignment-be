package com.microservices.courseservice.core.model;

import com.microservices.courseservice.core.type.Category;
import com.microservices.courseservice.core.type.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "course")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {

    @Id
    private String courseId;

    private String courseName;

    private Category category;

    private double price;

    private String courseDescription;

    private String instructorId;

    private Status status;

    private boolean isActive = true;

}