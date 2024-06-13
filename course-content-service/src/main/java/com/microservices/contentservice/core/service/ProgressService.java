package com.microservices.contentservice.core.service;

import com.microservices.contentservice.core.payload.ProgressCreateDto;
import com.microservices.contentservice.core.payload.common.ResponseEntityDto;

public interface ProgressService {

    ResponseEntityDto getAllProgress(String courseId);
    ResponseEntityDto getOverallProgress(String courseId);
    ResponseEntityDto addProgress(ProgressCreateDto progressCreateDto);
}
