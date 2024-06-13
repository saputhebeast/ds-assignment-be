package com.microservices.contentservice.controller;

import com.microservices.contentservice.core.payload.ProgressCreateDto;
import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import com.microservices.contentservice.core.service.ProgressService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
public class ProgressController {

    @NonNull
    private final ProgressService progressService;

    @GetMapping("/my")
    @PreAuthorize("hasAnyAuthority('LEARNER')")
    public ResponseEntity<ResponseEntityDto> getAllProgress(@RequestParam(value = "courseId", required = false) String courseId) {
        ResponseEntityDto response = progressService.getAllProgress(courseId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<ResponseEntityDto> getOverallProgress(@RequestParam(required = false)String courseId) {
        ResponseEntityDto response = progressService.getOverallProgress(courseId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('LEARNER')")
    public ResponseEntity<ResponseEntityDto> addProgress(@RequestBody ProgressCreateDto progressCreateDto) {
        ResponseEntityDto response = progressService.addProgress(progressCreateDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
