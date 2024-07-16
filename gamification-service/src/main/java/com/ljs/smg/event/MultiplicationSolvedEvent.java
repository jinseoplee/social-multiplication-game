package com.ljs.smg.event;

public record MultiplicationSolvedEvent(
        String userId,
        Integer multiplicationAttemptId,
        boolean isCorrect
) {
}
