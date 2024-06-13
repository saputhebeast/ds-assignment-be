package com.microservices.notification.service.dto;

import lombok.Data;

@Data
public class TextMessageDto {

    String message;
    String receiver;
}
