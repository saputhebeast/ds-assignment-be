package com.microservices.enrollmentservice.core.payload;

import com.microservices.enrollmentservice.core.type.CompletionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class EnrollmentResponseDto {

    private String id;
    private CourseResponseDto course;
    private UserResponseDto user;
    private LocalDate enrollmentDate;
    private CompletionStatus completionStatus;
    private boolean isActive;
}
