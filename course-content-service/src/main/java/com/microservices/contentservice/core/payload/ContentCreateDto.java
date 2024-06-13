package com.microservices.contentservice.core.payload;

import com.microservices.contentservice.core.type.ContentType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ContentCreateDto {
    @NonNull
    private String contentTitle;
    @NonNull
    private ContentType contentType;
    @NonNull
    private String contentAccessLink;
}
