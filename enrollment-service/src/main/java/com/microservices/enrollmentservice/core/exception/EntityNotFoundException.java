package com.microservices.enrollmentservice.core.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() { 
        super(); 
    }

    public EntityNotFoundException(String message) { 
        super(message); 
    }
}
