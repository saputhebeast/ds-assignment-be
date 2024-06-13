package com.microservices.enrollmentservice.core.repository;

import com.microservices.enrollmentservice.core.model.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EnrollmentRepository extends MongoRepository <Enrollment, String> {

    List<Enrollment> findAllByUserIdAndIsActiveTrue(String userId);
}
