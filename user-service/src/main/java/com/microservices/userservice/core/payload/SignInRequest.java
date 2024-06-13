package com.microservices.userservice.core.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    @NonNull
    String email;
    @NonNull
    String password;
}
