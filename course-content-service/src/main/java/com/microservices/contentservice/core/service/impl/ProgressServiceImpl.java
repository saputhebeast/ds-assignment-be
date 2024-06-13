package com.microservices.contentservice.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.contentservice.client.CourseServiceClient;
import com.microservices.contentservice.client.EnrollmentServiceClient;
import com.microservices.contentservice.core.exception.ModuleException;
import com.microservices.contentservice.core.model.Content;
import com.microservices.contentservice.core.model.Progress;
import com.microservices.contentservice.core.payload.CourseResponseDto;
import com.microservices.contentservice.core.payload.ProgressCreateDto;
import com.microservices.contentservice.core.payload.ProgressSummaryDto;
import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import com.microservices.contentservice.core.repository.ContentRepository;
import com.microservices.contentservice.core.repository.ProgressRepository;
import com.microservices.contentservice.core.service.ProgressService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

    @NonNull
    private final EnrollmentServiceClient enrollmentServiceClient;
    @NonNull
    private final CourseServiceClient courseServiceClient;
    @NonNull
    private final ProgressRepository progressRepository;
    @NonNull
    private final ContentRepository contentRepository;
    @NonNull
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntityDto getAllProgress(String courseId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Progress> progressList = progressRepository.findAllByUserIdAndCourseId(userId, courseId);
        List<Content> contentList = contentRepository.findAllByCourseId(courseId);

        int viewedContentCount = progressList.stream().map(Progress::getContentId).collect(Collectors.toSet()).size();
        int totalContentCount = contentList.size();
        ProgressSummaryDto progressSummaryDto = new ProgressSummaryDto();
        progressSummaryDto.setTotalContentCount(totalContentCount);
        progressSummaryDto.setViewedContentCount(viewedContentCount);
        progressSummaryDto.setProgressPercentage((float) ((viewedContentCount / totalContentCount) * 100.0));

        return new ResponseEntityDto(false, progressSummaryDto);
    }

    @Override
    public ResponseEntityDto getOverallProgress(String courseId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        if (courseId != null) {
            ResponseEntityDto response = courseServiceClient.getCourseById(courseId);
            if (Objects.equals(response.getStatus(), ResponseEntityDto.UNSUCCESSFUL)) {
                throw new ModuleException("Course not found");
            }
            Object courseResponse = response.getResults().get(0);
            CourseResponseDto course = null;
            try {
                String responseJson = objectMapper.writeValueAsString(courseResponse);
                course = objectMapper.readValue(responseJson, CourseResponseDto.class);
            } catch (JsonProcessingException e) {
                throw new ModuleException(e.getMessage());
            }

            if (!Objects.equals(course.getInstructor().getId(), userId)) {
                throw new ModuleException("Only course owner can view");
            }

            List<Progress> progressList = progressRepository.findAllByCourseId(courseId);
            List<Content> contentList = contentRepository.findAllByCourseId(courseId);

            int viewedContentCount = progressList.stream().map(Progress::getContentId).collect(Collectors.toSet()).size();
            int totalContentCount = contentList.size();

            ProgressSummaryDto progressSummaryDto = new ProgressSummaryDto();
            progressSummaryDto.setCourseResponseDto(course);
            progressSummaryDto.setTotalContentCount(totalContentCount);
            progressSummaryDto.setViewedContentCount(viewedContentCount);
            progressSummaryDto.setProgressPercentage(totalContentCount > 0 ? (float) ((viewedContentCount / totalContentCount) * 100.0) : 0);

            return new ResponseEntityDto(false, progressSummaryDto);
        } else {
            ResponseEntityDto response = courseServiceClient.getCourseByInstructorId(userId);
            if (Objects.equals(response.getStatus(), ResponseEntityDto.UNSUCCESSFUL)) {
                throw new ModuleException("Courses not found");
            }

            List<ProgressSummaryDto> progressSummaryList = new ArrayList<>();
            try {
                List<Object> courseResponseList = response.getResults();
                List<CourseResponseDto> courseList = new ArrayList<>();
                for (Object courseObj: courseResponseList) {
                    courseList.add(objectMapper.readValue(objectMapper.writeValueAsString(courseObj), CourseResponseDto.class));
                }

                List<String> owningCourses = courseList.stream().map(CourseResponseDto::getId).toList();
                List<Progress> progressList = progressRepository.findAllByCourseIdIsIn(owningCourses);
                List<Content> contentList = contentRepository.findAllByCourseIdIsIn(owningCourses);

                for(CourseResponseDto course: courseList) {
                    int totalContent = contentList.stream().filter(content -> content.getCourseId().equals(course.getId())).collect(Collectors.toSet()).size();
                    int viewedContent = progressList.stream().filter(progress -> progress.getCourseId().equals(course.getId())).collect(Collectors.toSet()).size();
                    ProgressSummaryDto progressSummaryDto = new ProgressSummaryDto();
                    progressSummaryDto.setCourseResponseDto(course);
                    progressSummaryDto.setTotalContentCount(totalContent);
                    progressSummaryDto.setViewedContentCount(viewedContent);
                    progressSummaryDto.setProgressPercentage(totalContent > 0 ? (float) ((viewedContent / totalContent) * 100) : 0);
                    progressSummaryList.add(progressSummaryDto);
                }

            } catch (JsonProcessingException e) {
                throw new ModuleException(e.getMessage());
            }
            return new ResponseEntityDto(false, progressSummaryList);
        }
    }

    private void validateCourseEnrolled(String userId, String courseId) {
        ResponseEntityDto response = enrollmentServiceClient.getEnrolledCourseIds(userId);
        if (Objects.equals(response.getStatus(), ResponseEntityDto.UNSUCCESSFUL)) {
            throw new ModuleException("Failed to fetch enrollments of user");
        }
        List<Object> courseIds = response.getResults();
        if (courseIds.stream().noneMatch(id -> id.toString().equals(courseId))) {
            throw new ModuleException("You are not enrolled to this course");
        }
    }

    @Override
    public ResponseEntityDto addProgress(ProgressCreateDto progressCreateDto) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ResponseEntityDto response = courseServiceClient.getCourseById(progressCreateDto.getCourseId());
        if (Objects.equals(response.getStatus(), ResponseEntityDto.UNSUCCESSFUL)) {
            throw new ModuleException("Course not found");
        }

        validateCourseEnrolled(userId, progressCreateDto.getCourseId());
        Optional<Content> optionalContent = contentRepository.findById(progressCreateDto.getContentId());
        if (optionalContent.isEmpty()) {
            throw new ModuleException("Content not found");
        }

        Progress progress = new Progress();
        progress.setContentId(optionalContent.get());
        progress.setCourseId(progressCreateDto.getCourseId());
        progress.setUserId(userId);

        progressRepository.save(progress);

        return new ResponseEntityDto(false, "Added successfully");
    }
}
