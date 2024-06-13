package com.microservices.courseservice.core.service.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.courseservice.core.exception.ModuleException;
import com.microservices.courseservice.core.payload.fiegn.ResponseEntityDto;
import com.microservices.courseservice.core.payload.fiegn.UserResponseDto;
import com.microservices.courseservice.core.payload.fiegn.enums.Role;
import com.microservices.courseservice.core.service.client.UserServiceClient;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserCache {

    @NonNull
    private final UserServiceClient userServiceClient;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static Map<String, UserResponseDto> userMap = new HashMap<>();
    private static List<UserResponseDto> userResponseDtos = new ArrayList<>();

    @Autowired
    public UserCache(@NonNull UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    public UserResponseDto getUserResponseDto(String userId) {
        if (userMap.isEmpty()) {
            ResponseEntityDto responseEntity = userServiceClient.getAllUserByOptionalRole(Role.INSTRUCTOR);

            if (responseEntity != null && responseEntity.getStatus().equals("successful")) {
                for (Object userResponse: responseEntity.getResults()) {
                    UserResponseDto user = null;
                    try {
                        user = objectMapper.readValue(objectMapper.writeValueAsString(userResponse), UserResponseDto.class);
                        userResponseDtos.add(user);
                    } catch (JsonProcessingException e) {
                        throw new ModuleException(e.getMessage());
                    }
                    userMap.put(user.getId(), user);
                }
                return userResponseDtos.stream()
                        .anyMatch(user -> user.getId().equals(userId)) ? userMap.get(userId) : null;
            } else {
                throw new IllegalStateException("Error: Unable to fetch user data.");
            }
        } else {
            if (userMap.get(userId) == null) {
                userMap.clear();
                getUserResponseDto(userId);
            } else {
                return userMap.get(userId);
            }
        }
      return null;
    }
}
