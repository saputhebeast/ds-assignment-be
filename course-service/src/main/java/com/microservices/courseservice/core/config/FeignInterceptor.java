package com.microservices.courseservice.core.config;

import com.microservices.courseservice.core.payload.fiegn.UserResponseDto;
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
        requestTemplate.header("role", user.getRole().name());
        requestTemplate.header("name", user.getName());
    }

    private String getAuthToken() {
        return JwtAuthenticationFilter.jwtToken.get();
    }
}
