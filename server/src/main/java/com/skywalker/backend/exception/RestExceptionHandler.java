package com.skywalker.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    // Custom application exceptions
    @ExceptionHandler(OurException.class)
    public ResponseEntity<Map<String, Object>> handleOurException(OurException ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("statusCode", 400); // always first
        error.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    // Handles JSON parse errors
    @ExceptionHandler(com.fasterxml.jackson.databind.exc.InvalidFormatException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidFormatException(com.fasterxml.jackson.databind.exc.InvalidFormatException ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("statusCode", 400);
        error.put("message", "Invalid value: " + ex.getValue() + ". " + ex.getOriginalMessage());
        return ResponseEntity.badRequest().body(error);
    }

    // Handles all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("statusCode", 500);
        error.put("message", ex.getMessage());
        return ResponseEntity.status(500).body(error);
    }
}