package com.ljs.smg.multiplication;

import com.ljs.smg.client.UserClient;
import com.ljs.smg.client.UserExistsResponse;
import com.ljs.smg.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class MultiplicationServiceTest {
    private MultiplicationService multiplicationService;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Mock
    private MultiplicationAttemptRepository multiplicationAttemptRepository;

    @Mock
    private MultiplicationMapper multiplicationMapper;

    @Mock
    private UserClient userClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        multiplicationService = new MultiplicationService(
                randomGeneratorService,
                multiplicationAttemptRepository,
                multiplicationMapper,
                userClient
        );
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

    @Test
    public void checkCorrectAttemptTest() {
        // given
        String userId = "ljs";
        int factorA = 2;
        int factorB = 7;
        int answer = 14;

        MultiplicationAttemptRequest request = new MultiplicationAttemptRequest(userId, factorA, factorB, answer);
        MultiplicationAttempt attempt = MultiplicationAttempt.builder()
                .userId(request.userId())
                .multiplication(
                        Multiplication.builder()
                                .factorA(request.factorA())
                                .factorB(request.factorB())
                                .build()
                )
                .answer(request.answer())
                .isCorrect(true)
                .build();

        given(userClient.checkUserExists(userId)).willReturn(new UserExistsResponse(true));
        given(multiplicationMapper.toMultiplicationAttempt(request, true)).willReturn(attempt);

        // when
        MultiplicationAttemptResponse response = multiplicationService.checkAttempt(request);

        // then
        assertTrue(response.isCorrect());
        verify(multiplicationAttemptRepository).save(attempt);
    }

    @Test
    public void checkIncorrectAttemptTest() {
        // given
        String userId = "ljs";
        int factorA = 3;
        int factorB = 9;
        int answer = 28;

        MultiplicationAttemptRequest request = new MultiplicationAttemptRequest(userId, factorA, factorB, answer);
        MultiplicationAttempt attempt = MultiplicationAttempt.builder()
                .userId(request.userId())
                .multiplication(
                        Multiplication.builder()
                                .factorA(request.factorA())
                                .factorB(request.factorB())
                                .build()
                )
                .answer(request.answer())
                .isCorrect(false)
                .build();

        given(userClient.checkUserExists(userId)).willReturn(new UserExistsResponse(true));
        given(multiplicationMapper.toMultiplicationAttempt(request, false)).willReturn(attempt);

        // when
        MultiplicationAttemptResponse response = multiplicationService.checkAttempt(request);

        // then
        assertFalse(response.isCorrect());
        verify(multiplicationAttemptRepository).save(attempt);
    }

    @Test
    public void checkAttemptWithNonExistentUserTest() {
        // given
        String userId = "ljs";
        int factorA = 2;
        int factorB = 5;
        int answer = 10;

        MultiplicationAttemptRequest request = new MultiplicationAttemptRequest(userId, factorA, factorB, answer);

        given(userClient.checkUserExists(userId)).willReturn(new UserExistsResponse(false));

        // when, then
        assertThrows(UserNotFoundException.class, () -> multiplicationService.checkAttempt(request));
    }
}