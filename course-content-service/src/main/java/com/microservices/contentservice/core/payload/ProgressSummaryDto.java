package com.microservices.contentservice.core.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgressSummaryDto {
    private Integer totalContentCount;
    private Integer viewedContentCount;
    private Float progressPercentage;
    private CourseResponseDto courseResponseDto;
}
