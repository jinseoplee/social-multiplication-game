package com.ljs.smg.multiplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

class MultiplicationServiceTest {
    private MultiplicationService multiplicationService;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        multiplicationService = new MultiplicationService(randomGeneratorService);
    }

    @Test
    public void createRandomMultiplicationTest() {
        // given
        int factorA = 2;
        int factorB = 5;

        given(randomGeneratorService.generateRandomFactor()).willReturn(factorA, factorB);

        // when
        MultiplicationResponse response = multiplicationService.createRandomMultiplication();

        // then
        assertEquals(factorA, response.factorA());
        assertEquals(factorB, response.factorB());
    }
}