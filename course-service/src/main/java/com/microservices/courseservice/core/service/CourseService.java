package com.microservices.courseservice.core.service;

import com.microservices.courseservice.core.payload.CourseRequestDto;
import com.microservices.courseservice.core.payload.common.ResponseEntityDto;

public interface CourseService {

    ResponseEntityDto addCourse(CourseRequestDto courseRequestDto);

    ResponseEntityDto updateCourse(String courseId, CourseRequestDto courseRequestDto);

    ResponseEntityDto getCourseByCourseId(String courseId);

    ResponseEntityDto getCourseByCourseName(String courseName);

    ResponseEntityDto getAllCourses();

    ResponseEntityDto getCoursesByInstructor(String instructorId);
}
