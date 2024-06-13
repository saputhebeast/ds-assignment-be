package com.microservices.contentservice.core.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentUpdateDto {
    private String contentTitle;
    private String contentAccessLink;
    private Boolean visible;
}
