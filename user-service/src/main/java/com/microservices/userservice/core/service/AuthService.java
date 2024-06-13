package com.microservices.userservice.core.service;

import com.microservices.userservice.core.payload.SignUpRequest;
import com.microservices.userservice.core.payload.common.ResponseEntityDto;

public interface AuthService {

    ResponseEntityDto signUp(SignUpRequest signUpRequest);

    ResponseEntityDto signIn(String email, String password);
}

