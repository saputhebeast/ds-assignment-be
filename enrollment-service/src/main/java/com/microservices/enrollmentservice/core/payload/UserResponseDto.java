package com.microservices.enrollmentservice.core.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String id;
    private String name;
    private String email;
    private String role;
}
