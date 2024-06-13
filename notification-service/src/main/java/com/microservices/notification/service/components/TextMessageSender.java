package com.microservices.notification.service.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TextMessageSender {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${notification.text.api.url}")
    private String apiUrl;

    @Value("${notification.text.user.id}")
    private String userId;

    @Value("${notification.text.api.key}")
    private String apiKey;


    public void sendTextMessage(String toNumber, String messageBody) {
        String url = apiUrl.replace("[USER_ID]", userId)
                .replace("[API_KEY]", apiKey)
                .replace("[TO]", toNumber)
                .replace("[MESSAGE]", messageBody);

        String response = restTemplate.getForObject(url, String.class);
        System.out.println("API Response: " + response);
    }
}
