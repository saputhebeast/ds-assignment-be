package com.microservices.courseservice.core.payload.fiegn;


import com.microservices.courseservice.core.payload.fiegn.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    String id;
    String name;
    String email;
    Role role;
}
