package com.ljs.smg.multiplication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MultiplicationService {

    private final RandomGeneratorService randomGeneratorService;

    public MultiplicationResponse createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new MultiplicationResponse(factorA, factorB);
    }
}
