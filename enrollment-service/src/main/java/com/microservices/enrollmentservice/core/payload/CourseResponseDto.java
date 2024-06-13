package com.microservices.enrollmentservice.core.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseResponseDto {
    private String id;
    private String courseName;
    private String courseDescription;
//    private String instructor;
    private String category;
//    private Double price;
//    private String status;
    private boolean isActive;
}
