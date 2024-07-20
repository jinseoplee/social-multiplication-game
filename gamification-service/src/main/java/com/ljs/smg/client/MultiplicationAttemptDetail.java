package com.ljs.smg.client;

public record MultiplicationAttemptDetail(
        int id,
        int factorA,
        int factorB,
        int answer,
        boolean isCorrect
) {
}
