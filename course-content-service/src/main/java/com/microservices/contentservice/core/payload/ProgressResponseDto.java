package com.microservices.contentservice.core.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProgressResponseDto {
    private String contentId;
    private String courseId;
    private String accessedDate;
}
