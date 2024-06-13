package com.microservices.enrollmentservice.controller;

import com.microservices.enrollmentservice.core.payload.EnrollmentRequestDto;
import com.microservices.enrollmentservice.core.payload.common.ResponseEntityDto;
import com.microservices.enrollmentservice.core.service.EnrollmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {

    @NonNull
    private final EnrollmentService enrollmentService;

    @PostMapping()
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<ResponseEntityDto> enrollToCourse(@RequestBody EnrollmentRequestDto enrollmentRequestDto) {
        ResponseEntityDto response = enrollmentService.addEnrollmentToCourse(enrollmentRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<ResponseEntityDto> unEnrollFromCourse(@PathVariable String id) {
        ResponseEntityDto response = enrollmentService.removeEnrollmentFromCourse(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEntityDto> enrollmentSummary(@PathVariable String id) {
        ResponseEntityDto response = enrollmentService.getEnrollmentSummary(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/of/{userId}")
    public ResponseEntity<ResponseEntityDto> getEnrollmentsByUserId(@PathVariable String userId) {
        ResponseEntityDto response = enrollmentService.getEnrolledCourseIdsByUserId(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/of/detailed/{userId}")
    public ResponseEntity<ResponseEntityDto> getDetailedEnrollmentsByUserId(@PathVariable String userId) {
        ResponseEntityDto response = enrollmentService.getDetailedEnrollmentsByUserId(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
