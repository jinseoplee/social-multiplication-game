package com.ljs.smg.multiplication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MultiplicationService {

    private final RandomGeneratorService randomGeneratorService;
    private final MultiplicationAttemptRepository multiplicationAttemptRepository;
    private final MultiplicationMapper multiplicationMapper;

    public MultiplicationResponse createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new MultiplicationResponse(factorA, factorB);
    }

    @Transactional
    public MultiplicationAttemptResponse checkAttempt(MultiplicationAttemptRequest request) {
        boolean isCorrect = request.answer() ==
                request.factorA() * request.factorB();

        MultiplicationAttempt attempt = multiplicationMapper.toMultiplicationAttempt(request);
        multiplicationAttemptRepository.save(attempt);

        return new MultiplicationAttemptResponse(isCorrect);
    }
}
