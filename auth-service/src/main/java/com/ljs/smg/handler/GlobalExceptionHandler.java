package com.ljs.smg.handler;

import com.ljs.smg.common.dto.ApiErrorResponse;
import com.ljs.smg.common.dto.ExtraApiErrorResponse;
import com.ljs.smg.exception.PasswordMismatchException;
import com.ljs.smg.exception.UserIdAlreadyExistsException;
import com.ljs.smg.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 상태 코드
    private final static int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
    private final static int NOT_FOUND = HttpStatus.NOT_FOUND.value();
    private final static int UNAUTHORIZED = HttpStatus.UNAUTHORIZED.value();
    private final static int INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();

    // 기본 메시지
    private final static String VALIDATION_FAIL_MESSAGE = "유효성 검사 실패";
    private final static String INVALID_JSON_FORMAT_MESSAGE = "유효하지 않은 JSON 형식";
    private final static String UNAUTHORIZED_MESSAGE = "인증 실패";
    private final static String INTERNAL_SERVER_ERROR_MESSAGE = "일시적인 장애입니다.";

    // 400
    @ExceptionHandler(UserIdAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleUserIdAlreadyExistsException(UserIdAlreadyExistsException e) {
        return buildResponseEntity(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handlePasswordException(PasswordMismatchException e) {
        return buildResponseEntity(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return buildResponseEntity(BAD_REQUEST, INVALID_JSON_FORMAT_MESSAGE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExtraApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return buildResponseEntity(BAD_REQUEST, VALIDATION_FAIL_MESSAGE, errors);
    }

    // 401
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(AuthenticationException e) {
        return buildResponseEntity(UNAUTHORIZED, UNAUTHORIZED_MESSAGE);
    }

    // 404
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        return buildResponseEntity(NOT_FOUND, e.getMessage());
    }

    // 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        log.error("서버 오류 발생: {}", e.getMessage(), e);
        return buildResponseEntity(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MESSAGE);
    }

    private ResponseEntity<ApiErrorResponse> buildResponseEntity(int status, String message) {
        ApiErrorResponse response = ApiErrorResponse.of(status, message);
        return ResponseEntity.status(status).body(response);
    }

    private ResponseEntity<ExtraApiErrorResponse> buildResponseEntity(int status, String message, Map<String, String> errors) {
        ExtraApiErrorResponse response = ExtraApiErrorResponse.of(status, message, errors);
        return ResponseEntity.status(status).body(response);
    }
}