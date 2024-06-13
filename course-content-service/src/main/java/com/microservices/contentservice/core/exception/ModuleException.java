package com.microservices.contentservice.core.exception;

public class ModuleException extends RuntimeException {

    public ModuleException() {
        super();
    }

    public ModuleException(String message) {
        super(message);
    }
}
