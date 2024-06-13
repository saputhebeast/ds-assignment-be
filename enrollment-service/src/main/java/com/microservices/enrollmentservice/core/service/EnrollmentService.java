package com.microservices.enrollmentservice.core.service;

import com.microservices.enrollmentservice.core.payload.EnrollmentRequestDto;
import com.microservices.enrollmentservice.core.payload.common.ResponseEntityDto;

public interface EnrollmentService {

    ResponseEntityDto addEnrollmentToCourse(EnrollmentRequestDto enrollmentRequestDto);

    ResponseEntityDto removeEnrollmentFromCourse(String enrollmentId);

    ResponseEntityDto getEnrollmentSummary(String enrollmentId);

    ResponseEntityDto getEnrolledCourseIdsByUserId(String userId);

    ResponseEntityDto getDetailedEnrollmentsByUserId(String userId);
}
