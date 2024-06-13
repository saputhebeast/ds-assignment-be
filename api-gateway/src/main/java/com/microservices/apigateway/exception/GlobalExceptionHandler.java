package com.microservices.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
class GlobalExceptionHandler {

    Logger exceptionLogger = Logger.getLogger("ExceptionHandler");

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ProblemDetail> handleEntityNotFoundExceptions(Exception e) {
        exceptionLogger.log(Level.SEVERE, e.getMessage());
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage())).build();
    }
}
