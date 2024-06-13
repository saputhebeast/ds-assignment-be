package com.microservices.userservice.core.service;

import com.microservices.userservice.core.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUserName(String token);

    String generateToken(User userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    public boolean isTokenExpired(String token);
}
