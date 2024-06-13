package com.microservices.courseservice.core.payload;

import com.microservices.courseservice.core.type.Category;
import com.microservices.courseservice.core.type.Status;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class CourseRequestDto {
    @NonNull
    private String courseName;

    @NonNull
    private String courseDescription;

    @NonNull
    private Category category;

    private double price;

    private Status status = Status.PUBLISHED;

    private boolean isActive = true;
}
