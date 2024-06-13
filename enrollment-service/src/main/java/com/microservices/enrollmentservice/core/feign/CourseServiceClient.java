package com.microservices.enrollmentservice.core.feign;

import com.microservices.enrollmentservice.core.payload.common.ResponseEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service", url = "${feign.url.course}")
public interface CourseServiceClient {
    @GetMapping("/api/v1/course/{id}")
    ResponseEntity<ResponseEntityDto> getCourseById(@PathVariable String id);
}
