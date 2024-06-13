package com.microservices.userservice.core.payload;

import com.microservices.userservice.core.type.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @NonNull
    String name;
    @NonNull
    String email;
    @NonNull
    String password;
    @NonNull
    Role role;
}
