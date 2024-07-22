package com.ljs.smg.exception;

public class MultiplicationAttemptNotFoundException extends RuntimeException {

    public MultiplicationAttemptNotFoundException() {
        super("곱셈 시도 결과가 존재하지 않습니다.");
    }
}
