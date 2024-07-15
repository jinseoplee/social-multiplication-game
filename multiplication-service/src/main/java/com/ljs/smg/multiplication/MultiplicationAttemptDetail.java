package com.ljs.smg.multiplication;

public record MultiplicationAttemptDetail(
        int id,
        int factorA,
        int factorB,
        int answer,
        boolean isCorrect
) {
}
