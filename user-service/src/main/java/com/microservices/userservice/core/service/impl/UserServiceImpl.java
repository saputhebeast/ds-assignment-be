package com.microservices.userservice.core.service.impl;

import com.microservices.userservice.core.exception.EntityNotFoundException;
import com.microservices.userservice.core.exception.ModuleException;
import com.microservices.userservice.core.model.User;
import com.microservices.userservice.core.payload.UserResponseDto;
import com.microservices.userservice.core.payload.common.ResponseEntityDto;
import com.microservices.userservice.core.repository.UserRepository;
import com.microservices.userservice.core.service.UserService;
import com.microservices.userservice.core.type.Role;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @NonNull
    private final UserRepository userRepository;
    @Override
    public UserDetailsService userDetailsService() {
        return username -> (UserDetails) userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User getCurrentUser() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findById(currentUser.getId());
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    @Override
    public UserResponseDto getMe() {
        User user = this.getCurrentUser();
        return new UserResponseDto(
                user.getId(), user.getName(), user.getEmail(), user.getRole()
        );
    }

    @Override
    public ResponseEntityDto getUserByEmail(String username) {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            UserResponseDto userResponseDto = new UserResponseDto(
                    user.get().getId(), user.get().getName(), user.get().getEmail(), user.get().getRole()
            );
            return new ResponseEntityDto(false, userResponseDto);
        } else {
            throw new ModuleException("User not found");
        }
    }

    @Override
    public ResponseEntityDto getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserResponseDto userResponseDto = new UserResponseDto(
                    user.get().getId(), user.get().getName(), user.get().getEmail(), user.get().getRole()
            );
            return new ResponseEntityDto(false, userResponseDto);
        }
        else {
            throw new EntityNotFoundException("User not found");
        }
    }

    @Override
    public ResponseEntityDto getAllUserByOptionalRole(Role role) {
        List<UserResponseDto> usersList = new ArrayList<>();
        if (role == null) {
            List<User> users = userRepository.findAll();
            users.forEach(user ->
                    usersList.add(new UserResponseDto(
                            user.getId(), user.getName(), user.getEmail(), user.getRole()
                    ))
            );
        } else {
            List<User> users = userRepository.findAllByRole(role);
            users.forEach(user ->
                    usersList.add(new UserResponseDto(
                            user.getId(), user.getName(), user.getEmail(), user.getRole()
                    ))
            );
        }
        return new ResponseEntityDto(false, usersList);
    }
}
