package com.example.auth.controller;

import com.example.auth.exception.ExistedUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(ExistedUserException.class)
    public String handleExistedUserException(ExistedUserException e) {
        log.debug(e.getMessage());
        return e.getMessage();
    }
}
