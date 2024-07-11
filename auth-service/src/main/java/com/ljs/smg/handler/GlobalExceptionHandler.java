package com.ljs.smg.handler;

import com.ljs.smg.exception.PasswordException;
import com.ljs.smg.exception.UserIdAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserIdAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handle(UserIdAlreadyExistsException e) {
        return buildErrorResponse("userId", e.getMessage());
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<ErrorResponse> handlePasswordNotEquals(PasswordException e) {
        return buildErrorResponse("password", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return buildErrorResponse(errors);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String field, String message) {
        Map<String, String> errors = new HashMap<>();
        errors.put(field, message);
        return buildErrorResponse(errors);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Map<String, String> errors) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }
}
