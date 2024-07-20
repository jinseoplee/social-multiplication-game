package com.ljs.smg.common.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class ExtraApiErrorResponse extends ApiErrorResponse {
    private final Map<String, String> errors;

    public ExtraApiErrorResponse(int status, String message, Map<String, String> errors) {
        super(status, message);
        this.errors = errors;
    }

    public static ExtraApiErrorResponse of(int status, String message, Map<String, String> errors) {
        return new ExtraApiErrorResponse(status, message, errors);
    }
}