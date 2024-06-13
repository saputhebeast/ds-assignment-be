package com.microservices.apigateway.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface UserService {

    JsonNode getCurrentUser(String token) throws InterruptedException;
}
