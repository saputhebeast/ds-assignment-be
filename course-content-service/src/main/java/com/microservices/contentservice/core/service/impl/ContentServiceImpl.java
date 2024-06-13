package com.microservices.contentservice.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.contentservice.client.CourseServiceClient;
import com.microservices.contentservice.client.EnrollmentServiceClient;
import com.microservices.contentservice.core.exception.ModuleException;
import com.microservices.contentservice.core.mapper.MapStructMapper;
import com.microservices.contentservice.core.model.Content;
import com.microservices.contentservice.core.payload.ContentCreateDto;
import com.microservices.contentservice.core.payload.ContentResponseDto;
import com.microservices.contentservice.core.payload.ContentUpdateDto;
import com.microservices.contentservice.core.payload.CourseResponseDto;
import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import com.microservices.contentservice.core.repository.ContentRepository;
import com.microservices.contentservice.core.service.ContentService;
import com.microservices.contentservice.core.type.ApprovalStatus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    @NonNull
    private final ContentRepository contentRepository;
    @NonNull
    private final MapStructMapper mapStructMapper;
    @NonNull
    private final CourseServiceClient courseServiceClient;
    @NonNull
    private final EnrollmentServiceClient enrollmentServiceClient;
    @NonNull
    private final ObjectMapper objectMapper;

    private String getCurrentUserId() {
        return  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    private String getCurrentUserRole() {
        return  (String) SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().getAuthority();
    }

    @Override
    public ResponseEntityDto getAllContentByCourseId(String courseId) {
        ResponseEntityDto response = courseServiceClient.getCourseById(courseId);

        if (Objects.equals(response.getStatus(), ResponseEntityDto.UNSUCCESSFUL)) {
            throw new ModuleException("Course not found");
        }
        Object responseObject = response.getResults().get(0);
        CourseResponseDto course = null;

        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                 .map(GrantedAuthority::getAuthority).toList().get(0);

        List<Content> contentList;
        if (role.equals("LEARNER")) {
            contentList = contentRepository.findAllByCourseIdAndApproved(courseId, ApprovalStatus.APPROVED);
            courseEnrollmentValidation(courseId, userId);
        } else {
            contentList = contentRepository.findAllByCourseId(courseId);
            if (role.equals("INSTRUCTOR")) {
                try {
                    String responseJson = objectMapper.writeValueAsString(responseObject);
                    course = objectMapper.readValue(responseJson, CourseResponseDto.class);
                    if (course.getInstructor() == null || !Objects.equals(course.getInstructor().getId(), getCurrentUserId())) {
                        throw new ModuleException("No permission to get course content");
                    }
                } catch (JsonProcessingException e) {
                    throw new ModuleException(e.getMessage());
                }
            }
        }

        List<ContentResponseDto> contentResponseDtoList = mapStructMapper.contentListToContentResponseDtoList(contentList);
        return new ResponseEntityDto(false, contentResponseDtoList);
    }

    private CourseResponseDto getCourseFromResponse(ResponseEntityDto response) {
        Object responseObject = response.getResults().get(0);
        CourseResponseDto course = null;
        try {
            String responseJson = objectMapper.writeValueAsString(responseObject);
            course = objectMapper.readValue(responseJson, CourseResponseDto.class);
            if (course.getInstructor() == null || !Objects.equals(course.getInstructor().getId(), getCurrentUserId())) {
                throw new ModuleException("No permission to get course content");
            }
        } catch (JsonProcessingException e) {
            throw new ModuleException(e.getMessage());
        }
        return course;
    }

    @Override
    public ResponseEntityDto addContentToCourse(String courseId, ContentCreateDto contentCreateDto) {
        Content contentToSave = mapStructMapper.contentCreateDtoToContent(contentCreateDto);
        ResponseEntityDto response = courseServiceClient.getCourseById(courseId);
        if (Objects.equals(response.getStatus(), ResponseEntityDto.UNSUCCESSFUL)) {
            throw new ModuleException("Course not found");
        }

        CourseResponseDto course = getCourseFromResponse(response);

        String userId = getCurrentUserId();
        if (course.getInstructor() == null || !userId.equals(course.getInstructor().getId())) {
            throw new ModuleException("Not authorised to add content to this course");
        }

        contentToSave.setCourseId(course.getId());
        contentToSave.setVisible(false);
        contentToSave.setActive(true);
        contentToSave.setApproved(ApprovalStatus.AWAITING);
        contentToSave = contentRepository.save(contentToSave);
        return new ResponseEntityDto(false, mapStructMapper.contentToContentResponseDto(contentToSave));
    }

    @Override
    public ResponseEntityDto updateContent(String id, ContentUpdateDto contentUpdateDto) {
        Optional<Content> optionalContent = contentRepository.findById(id);
        if (optionalContent.isEmpty())
            throw new ModuleException("Content not found to update");

        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        courseOwnerShipValidation(optionalContent.get().getCourseId(), userId);

        Content content = optionalContent.get();
        if (contentUpdateDto.getContentTitle() != null)
            content.setContentTitle(contentUpdateDto.getContentTitle());

        if (contentUpdateDto.getContentAccessLink() != null)
            content.setContentAccessLink(contentUpdateDto.getContentAccessLink());

        if (contentUpdateDto.getVisible() != null)
            content.setVisible(content.getVisible());

        content.setUpdatedAt(new Date());
        content = contentRepository.save(content);
        return new ResponseEntityDto(false, content);
    }

    @Override
    public ResponseEntityDto getContentById(String id) {
        Optional<Content> content = contentRepository.findById(id);
        if (content.isEmpty())
            throw new ModuleException("Course content not found");

        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (getCurrentUserRole().equals("LEARNER"))
            courseEnrollmentValidation(content.get().getCourseId(), userId);

        return new ResponseEntityDto(false, mapStructMapper.contentToContentResponseDto(content.get()));
    }

    private void courseOwnerShipValidation(String courseId, String userId) {
        ResponseEntityDto response = courseServiceClient.getCourseById(courseId);
        if (Objects.equals(response.getStatus(), ResponseEntityDto.UNSUCCESSFUL)) {
            throw new ModuleException("Course not found");
        } else {
            Object courseResponse = response.getResults().get(0);
            CourseResponseDto course = null;
            try {
                String responseJson = objectMapper.writeValueAsString(courseResponse);
                course = objectMapper.readValue(responseJson, CourseResponseDto.class);
            } catch (JsonProcessingException e) {
                log.error("courseOwnerShipValidation: Error occurred: {}", e.getMessage());
                throw new ModuleException(e.getMessage());
            }
            if (!Objects.equals(course.getInstructor().getId(), userId)) {
                throw new ModuleException("You dont have permission to perform this action");
            }
        }
    }

    private void courseEnrollmentValidation(String courseId, String userId) {
        ResponseEntityDto response = enrollmentServiceClient.getEnrolledCourseIds(userId);
        if (Objects.equals(response.getStatus(), ResponseEntityDto.UNSUCCESSFUL)) {
            throw new ModuleException("Failed to fetch enrollments");
        } else {
            List<Object> courseIds = response.getResults();
            if (courseIds.stream().map(Object::toString).noneMatch(id -> id.equals(courseId))) {
                throw new ModuleException("You are not enrolled to this course");
            }
        }
    }

    @Override
    public ResponseEntityDto archiveContent(String id) {
        Optional<Content> optionalContent = contentRepository.findById(id);
        if (optionalContent.isEmpty())
            throw new ModuleException("Course content not found");
        Content content = optionalContent.get();
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        courseOwnerShipValidation(content.getCourseId(), userId);
        content.setActive(false);
        content.setUpdatedAt(new Date());
        content = contentRepository.save(content);
        return new ResponseEntityDto(false, content);
    }

    @Override
    public ResponseEntityDto getAllContentAwaitingApproval() {
        List<Content> contentList = contentRepository.findAllByApproved(ApprovalStatus.AWAITING);
        List<ContentResponseDto> contentResponseDtoList = mapStructMapper.contentListToContentResponseDtoList(contentList);
        return new ResponseEntityDto(false, contentResponseDtoList);
    }

    @Override
    public ResponseEntityDto updateApprovalOfContent(String id, ApprovalStatus approvalStatus) {
        Optional<Content> optionalContent = contentRepository.findById(id);
        if (optionalContent.isEmpty())
            throw new ModuleException("Course content not found");
        Content content = optionalContent.get();
        content.setApproved(approvalStatus);
        content.setPublishedDate(new Date());
        return new ResponseEntityDto(false, mapStructMapper.contentToContentResponseDto(content));
    }
}
