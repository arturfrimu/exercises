package com.arturfrimu.exercisesback.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> allExceptions(Exception e) {
        System.out.println(e.getMessage());
        System.out.println(e.getCause());
        return new ResponseEntity<>("An error occurred: " + e.getMessage(), INTERNAL_SERVER_ERROR);
    }
}
