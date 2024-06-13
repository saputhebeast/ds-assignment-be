package com.microservices.userservice.controller;

import com.microservices.userservice.core.payload.SignInRequest;
import com.microservices.userservice.core.payload.SignUpRequest;
import com.microservices.userservice.core.payload.common.ResponseEntityDto;
import com.microservices.userservice.core.service.AuthService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @NonNull
    private final AuthService authService;

    @PostMapping(value = "sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntityDto register(@RequestBody SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }

    @PostMapping(value = "/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntityDto login(@RequestBody SignInRequest signInRequest) {
        return authService.signIn(signInRequest.getEmail(), signInRequest.getPassword());
    }
}
