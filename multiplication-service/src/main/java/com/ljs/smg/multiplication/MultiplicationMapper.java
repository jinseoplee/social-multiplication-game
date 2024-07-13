package com.ljs.smg.multiplication;

import org.springframework.stereotype.Component;

@Component
public class MultiplicationMapper {

    public MultiplicationAttempt toMultiplicationAttempt(MultiplicationAttemptRequest request) {
        return MultiplicationAttempt.builder()
                .userId(request.userId())
                .multiplication(
                        Multiplication.builder()
                                .factorA(request.factorA())
                                .factorB(request.factorB())
                                .build()
                )
                .answer(request.answer())
                .build();
    }
}
