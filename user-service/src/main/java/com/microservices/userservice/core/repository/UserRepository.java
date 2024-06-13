package com.microservices.userservice.core.repository;

import com.microservices.userservice.core.model.User;
import com.microservices.userservice.core.type.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String s);

    List<User> findAllByRole(Role role);
}
