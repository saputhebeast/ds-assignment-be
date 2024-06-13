package com.microservices.notification.service.dto;

import lombok.Data;

@Data
public class MailSenderDto {

    private String subject;
    private String message;
    private String receiver;
}
