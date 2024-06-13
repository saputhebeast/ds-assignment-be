package com.microservices.notification.service.service;

import com.microservices.notification.service.dto.MailSenderDto;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendMail(MailSenderDto mailSenderDto) throws MessagingException;
}
