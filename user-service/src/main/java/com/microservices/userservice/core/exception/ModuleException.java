package com.microservices.userservice.core.exception;

public class ModuleException extends RuntimeException {

    public ModuleException(String message) {
        super(message);
    }
}
