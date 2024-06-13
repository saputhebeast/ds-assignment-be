package com.microservices.enrollmentservice.core.model;

import com.microservices.enrollmentservice.core.type.CompletionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "enrollment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Enrollment {

    @Id
    private String id;

    private String courseId;

    private String userId;

    private LocalDate enrollmentDate;

    private CompletionStatus completionStatus;

    private boolean isActive;
}
