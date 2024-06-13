package com.microservices.notification.service.controller;

import com.microservices.notification.service.components.TextMessageSender;
import com.microservices.notification.service.dto.MailSenderDto;
import com.microservices.notification.service.dto.TextMessageDto;
import com.microservices.notification.service.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notification")
@Slf4j
public class NotificationSenderController {

    @Autowired
    TextMessageSender textMessageSender;

    @Autowired
    EmailService mailSenderService;

    @GetMapping("/text-sender")
    public void sendText(@RequestBody TextMessageDto textMessageDto){
        log.info("NotificationSenderController.sendText() invoked.");
        textMessageSender.sendTextMessage(textMessageDto.getReceiver(),textMessageDto.getMessage());
    }

    @GetMapping("/mail-sender")
    public void sendMail(@RequestBody MailSenderDto mailSenderDto) throws MessagingException {
        log.info("NotificationSenderController.sendMail() invoked.");
        mailSenderService.sendMail(mailSenderDto);
    }
}
