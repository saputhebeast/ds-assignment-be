package com.microservices.contentservice.core.repository;

import com.microservices.contentservice.core.model.Content;
import com.microservices.contentservice.core.type.ApprovalStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContentRepository extends MongoRepository<Content, String> {
    List<Content> findAllByCourseIdAndApproved(String courseId, ApprovalStatus approved);

    List<Content> findAllByCourseId(String courseId);

    List<Content> findAllByCourseIdIsIn(List<String> courseIds);

    List<Content> findAllByApproved(ApprovalStatus approvalStatus);
}
