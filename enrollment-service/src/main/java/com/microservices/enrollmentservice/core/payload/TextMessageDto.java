package com.microservices.enrollmentservice.core.payload;

import lombok.Data;

@Data
public class TextMessageDto {

    String message;
    String receiver;
}
