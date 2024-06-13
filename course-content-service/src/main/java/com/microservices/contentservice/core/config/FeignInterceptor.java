package com.microservices.contentservice.core.config;

import com.microservices.contentservice.core.payload.UserResponseDto;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", getAuthToken());

        UserResponseDto user = (UserResponseDto) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        requestTemplate.header("userId", user.getId());
        requestTemplate.header("userName", user.getEmail());
        requestTemplate.header("role", user.getRole());
        requestTemplate.header("name", user.getName());
    }

    private String getAuthToken() {
        return "Bearer " + JwtAuthenticationFilter.jwtToken.get();
    }
}
