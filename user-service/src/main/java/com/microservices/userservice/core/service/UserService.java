package com.microservices.userservice.core.service;

import com.microservices.userservice.core.model.User;
import com.microservices.userservice.core.payload.UserResponseDto;
import com.microservices.userservice.core.payload.common.ResponseEntityDto;
import com.microservices.userservice.core.type.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();

    User getCurrentUser();

    UserResponseDto getMe();

    ResponseEntityDto getUserByEmail(String email);

    ResponseEntityDto getUserById(String id);

    ResponseEntityDto getAllUserByOptionalRole(Role role);
}
