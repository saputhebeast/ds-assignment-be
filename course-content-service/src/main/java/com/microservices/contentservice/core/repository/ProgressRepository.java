package com.microservices.contentservice.core.repository;

import com.microservices.contentservice.core.model.Progress;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProgressRepository extends MongoRepository<Progress, String> {

    List<Progress> findAllByCourseId(String courseId);

    List<Progress> findAllByUserIdAndCourseId(String userId, String courseId);

    List<Progress> findAllByCourseIdIsIn(List<String> courseIds);
}
