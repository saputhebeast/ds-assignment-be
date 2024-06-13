package com.microservices.enrollmentservice.core.feign;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class NotificationServiceClient {
    @Value("${feign.url.notification}")
    private String notificationServiceAddress;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(notificationServiceAddress).build();
    }
}

//    @PostMapping("/v1/notification/mail-sender")
//    ResponseEntity<ResponseEntityDto> sendEmail(@RequestBody @NotNull String subject, @NotNull String message, @NotNull String receiver);
//
//    @PostMapping("/v1/notification/text-sender")
//    ResponseEntity<ResponseEntityDto> sendSMS(@RequestBody @NotNull String message, @NotNull String receiver);

