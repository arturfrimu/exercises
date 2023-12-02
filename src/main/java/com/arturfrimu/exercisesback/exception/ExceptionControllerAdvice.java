package com.arturfrimu.exercisesback.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<String> securityException(AccessDeniedException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>("Access denied. " + e.getMessage(), FORBIDDEN);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> allExceptions(Exception e) {
        log.info(e.getMessage());
        return new ResponseEntity<>("An error occurred: " + e.getMessage(), INTERNAL_SERVER_ERROR);
    }
}
