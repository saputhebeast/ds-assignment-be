package com.microservices.courseservice.controller;

import com.microservices.courseservice.core.payload.CourseRequestDto;
import com.microservices.courseservice.core.payload.common.ResponseEntityDto;
import com.microservices.courseservice.core.service.CourseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    @NonNull
    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<ResponseEntityDto> addCourse(@Valid @RequestBody CourseRequestDto courseRequestDto) {
        ResponseEntityDto response = courseService.addCourse(courseRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<ResponseEntityDto> updateCourse(@Valid @RequestBody CourseRequestDto courseRequestDto, @PathVariable String id) {
        ResponseEntityDto response = courseService.updateCourse(id, courseRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEntityDto> getCourseById(@PathVariable String id) {
        ResponseEntityDto response = courseService.getCourseByCourseId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseEntityDto> getCourseByCourseName(@PathVariable String name) {
        ResponseEntityDto response = courseService.getCourseByCourseName(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseEntityDto> getAllCourse() {
        ResponseEntityDto response = courseService.getAllCourses();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/instructor/{id}")
    public ResponseEntity<ResponseEntityDto> getAllCourseByInstructorId(@PathVariable String id) {
        ResponseEntityDto response = courseService.getCoursesByInstructor(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
