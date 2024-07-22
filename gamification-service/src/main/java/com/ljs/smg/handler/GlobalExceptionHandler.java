package com.ljs.smg.handler;

import com.ljs.smg.common.dto.ApiErrorResponse;
import com.ljs.smg.common.dto.ExtraApiErrorResponse;
import com.ljs.smg.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 상태 코드
    private final static int NOT_FOUND = HttpStatus.NOT_FOUND.value();
    private final static int INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();

    // 기본 메시지
    private final static String INTERNAL_SERVER_ERROR_MESSAGE = "일시적인 장애입니다.";

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