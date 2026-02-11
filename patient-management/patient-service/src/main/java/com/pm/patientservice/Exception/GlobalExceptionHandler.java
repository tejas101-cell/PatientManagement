package com.pm.patientservice.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // this .class will handle all the validation exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception){
        // this is returned as the json
        // this small json will be passed as a response to the client instead of the big json
        Map<String, String> errors = new HashMap<>();

        // this statement will store all the error fields and messages into the errors hashMap
        exception.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        // returning the hashMap in which all the values were added
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExists(EmailAlreadyExistsException exception){
        // for debugging
        log.warn("Email address already exists {}", exception.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Email already exists");

        return ResponseEntity.ok().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFoundException(PatientNotFoundException exception){
        log.warn("Patient not found {}", exception.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Patient not found");

        return ResponseEntity.ok().body(errors);
    }
}
