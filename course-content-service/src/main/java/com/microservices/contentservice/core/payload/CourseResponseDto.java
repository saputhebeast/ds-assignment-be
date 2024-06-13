package com.microservices.contentservice.core.payload;

import com.microservices.contentservice.core.type.Category;
import com.microservices.contentservice.core.type.Status;
import lombok.Data;

@Data
public class CourseResponseDto {

    private String id;
    private String courseName;
    private String courseDescription;
    private InstructorResponseDto instructor;
    private Category category;
    private double price;
    private Status status;
    private boolean isActive;
}
