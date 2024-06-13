package com.microservices.contentservice.core.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstructorResponseDto {

    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String about;
    private String experience;
    private String socialMedia;
}
