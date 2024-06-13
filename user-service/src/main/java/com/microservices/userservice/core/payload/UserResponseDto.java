package com.microservices.userservice.core.payload;

import com.microservices.userservice.core.type.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserResponseDto {
    String id;
    String name;
    String email;
    Role role;
}
