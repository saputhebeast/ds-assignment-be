package com.microservices.contentservice.core.payload;

import com.microservices.contentservice.core.type.ContentType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ContentResponseDto {
    private String id;
    private String courseId;
    private String contentTitle;
    private ContentType contentType;
    private Boolean visible;
    private String contentAccessLink;
    private Boolean active;
    private Date publishedDate;
    private Date createdDate;
    private Date updatedDate;
}
