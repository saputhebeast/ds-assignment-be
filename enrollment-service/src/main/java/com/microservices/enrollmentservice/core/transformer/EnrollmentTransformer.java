package com.microservices.enrollmentservice.core.transformer;

import com.microservices.enrollmentservice.core.model.Enrollment;
import com.microservices.enrollmentservice.core.payload.CourseResponseDto;
import com.microservices.enrollmentservice.core.payload.EnrollmentRequestDto;
import com.microservices.enrollmentservice.core.payload.EnrollmentResponseDto;
import com.microservices.enrollmentservice.core.payload.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentTransformer {

    public EnrollmentResponseDto transformEnrollmentDto(Enrollment enrollment, CourseResponseDto courseResponseDto, UserResponseDto userResponseDto) {
        EnrollmentResponseDto enrollmentResponseDto = new EnrollmentResponseDto();
        enrollmentResponseDto.setId(enrollment.getId());
        enrollmentResponseDto.setCourse(courseResponseDto);
        enrollmentResponseDto.setUser(userResponseDto);
        enrollmentResponseDto.setEnrollmentDate(enrollment.getEnrollmentDate());
        enrollmentResponseDto.setCompletionStatus(enrollment.getCompletionStatus());
        enrollmentResponseDto.setActive(enrollment.isActive());
        return enrollmentResponseDto;
    }

    public Enrollment reverseTransform(EnrollmentRequestDto enrollmentRequestDto, String userId) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourseId(enrollmentRequestDto.getCourseId());
        enrollment.setUserId(userId);
        enrollment.setEnrollmentDate(enrollmentRequestDto.getEnrollmentDate());
        enrollment.setCompletionStatus(enrollmentRequestDto.getCompletionStatus());
        enrollment.setActive(enrollmentRequestDto.isActive());
        return enrollment;
    }
}
