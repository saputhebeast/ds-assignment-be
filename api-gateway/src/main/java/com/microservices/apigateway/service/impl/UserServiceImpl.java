package com.microservices.apigateway.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.apigateway.exception.UnAuthorizedException;
import com.microservices.apigateway.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    @NonNull
    private final RestTemplate restTemplate;
    @NonNull
    private final ObjectMapper objectMapper;
    @Value("${address.base.user-service}")
    private String userServiceAddress;

    @SneakyThrows
    @Override
    public JsonNode getCurrentUser(String token) {
        try {
            log.info("getCurrentUser: Execution started.");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(HttpHeaders.AUTHORIZATION, token);
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<Object> responseEntity = restTemplate.exchange(
                    userServiceAddress + "/api/v1/user/me",
                    HttpMethod.GET,
                    requestEntity,
                    Object.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Object user = responseEntity.getBody();
                log.info("getCurrentUser: Execution Completed");
                return getJson(user);
            } else {
                throw new UnAuthorizedException(getJson(responseEntity.getBody()).get("results").get(0).get("message").asText());
            }
        } catch (Exception e) {
            log.error("getCurrentUser: Error occurred: {}", e.getMessage());
            throw new UnAuthorizedException(e.getMessage());
        }
    }

    JsonNode getJson(Object user) {
        try {
            return objectMapper.readTree(objectMapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
