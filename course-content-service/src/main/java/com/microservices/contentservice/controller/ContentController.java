package com.microservices.contentservice.controller;

import com.microservices.contentservice.core.payload.ContentCreateDto;
import com.microservices.contentservice.core.payload.ContentUpdateDto;
import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import com.microservices.contentservice.core.service.ContentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/course-content")
@RequiredArgsConstructor
public class ContentController {

    @NonNull
    private final ContentService contentService;

    @GetMapping("of/{courseId}")
    public ResponseEntity<ResponseEntityDto> getAllContent(@PathVariable String courseId) {
        ResponseEntityDto response = contentService.getAllContentByCourseId(courseId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("of/{courseId}")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR')")
    public ResponseEntity<ResponseEntityDto> addContentToCourse(@PathVariable String courseId, @RequestBody ContentCreateDto contentCreateDto) {
        ResponseEntityDto response = contentService.addContentToCourse(courseId, contentCreateDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR')")
    public ResponseEntity<ResponseEntityDto> updateContent(@PathVariable String id, @RequestBody ContentUpdateDto contentUpdateDto) {
        ResponseEntityDto response = contentService.updateContent(id, contentUpdateDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR')")
    public ResponseEntity<ResponseEntityDto> deleteContent(@PathVariable String id) {
        ResponseEntityDto response = contentService.archiveContent(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseEntityDto> getContentById(@PathVariable String id) {
        ResponseEntityDto response = contentService.getContentById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
