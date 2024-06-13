package com.microservices.enrollmentservice.core.payload;

import lombok.Data;

@Data
public class MailSenderDto {

    private String subject;
    private String message;
    private String receiver;
}
