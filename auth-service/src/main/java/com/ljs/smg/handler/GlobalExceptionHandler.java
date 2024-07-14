package com.ljs.smg.handler;

import com.ljs.smg.exception.PasswordException;
import com.ljs.smg.exception.UserIdAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
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
        return createErrorResponse("userId", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<ErrorResponse> handle(PasswordException e) {
        return createErrorResponse("password", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handle(AuthenticationException e) {
        return createErrorResponse("authentication", "아이디 또는 비밀번호를 확인해 주세요.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return createErrorResponse(errors, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(String field, String message, HttpStatus status) {
        Map<String, String> errors = new HashMap<>();
        errors.put(field, message);
        return createErrorResponse(errors, status);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(Map<String, String> errors, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(errors));
    }
}
