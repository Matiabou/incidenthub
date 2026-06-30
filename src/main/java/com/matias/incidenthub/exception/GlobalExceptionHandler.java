package com.matias.incidenthub.exception;

import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            NoHandlerFoundException.class
    )

    public ResponseEntity<String>
    handleNotFound(

            NoHandlerFoundException ex

    ) {

        return ResponseEntity
                .status(
                        HttpStatus.NOT_FOUND
                )
                .body(
                        "Endpoint not found"
                );

    }

}