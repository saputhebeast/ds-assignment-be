package com.microservices.apigateway.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnAuthorizedException extends Exception {

    public UnAuthorizedException(String message) {
        super(message);
    }
}
