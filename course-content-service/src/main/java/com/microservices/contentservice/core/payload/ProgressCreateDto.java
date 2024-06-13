package com.microservices.contentservice.core.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProgressCreateDto {
    @NonNull
    private String contentId;
    @NonNull
    private String courseId;
}
