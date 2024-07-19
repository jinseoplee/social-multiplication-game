package com.ljs.smg.multiplication;

import com.ljs.smg.client.UserClient;
import com.ljs.smg.client.UserExistsResponse;
import com.ljs.smg.event.EventDispatcher;
import com.ljs.smg.event.MultiplicationSolvedEvent;
import com.ljs.smg.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MultiplicationServiceTest {

    @InjectMocks
    private MultiplicationService multiplicationService;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Mock
    private MultiplicationAttemptRepository multiplicationAttemptRepository;

    @Mock
    private MultiplicationMapper multiplicationMapper;

    @Mock
    private UserClient userClient;

    @Mock
    private EventDispatcher eventDispatcher;

    @Test
    void createRandomMultiplicationTest() {
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
    void checkCorrectAttemptTest() {
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
        verify(eventDispatcher).send(any(MultiplicationSolvedEvent.class));
    }

    @Test
    void checkIncorrectAttemptTest() {
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
        verify(eventDispatcher).send(any(MultiplicationSolvedEvent.class));
    }

    @Test
    void checkAttemptWithNonExistentUserTest() {
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

    @Test
    void shouldReturnRecentAttemptsWhenUserExists() {
        // given
        String userId = "ljs";
        int factorA = 2;
        int factorB = 5;
        int answer = 10;
        boolean isCorrect = true;

        Multiplication multiplication = new Multiplication(23, factorA, factorB);
        List<MultiplicationAttempt> attempts = List.of(
                MultiplicationAttempt.builder()
                        .id(25)
                        .userId(userId)
                        .multiplication(multiplication)
                        .answer(answer)
                        .isCorrect(isCorrect)
                        .build()
        );

        given(userClient.checkUserExists(userId)).willReturn(new UserExistsResponse(true));
        given(multiplicationAttemptRepository.findTop5ByUserIdOrderByIdDesc(userId)).willReturn(attempts);

        List<MultiplicationAttemptDetail> multiplications = List.of(
                new MultiplicationAttemptDetail(25, factorA, factorB, answer, isCorrect)
        );

        RecentMultiplicationAttemptResponse expectedResponse = new RecentMultiplicationAttemptResponse(
                userId,
                multiplications
        );

        // when
        RecentMultiplicationAttemptResponse actualResponse = multiplicationService.findRecentAttempts(userId);

        // then
        assertEquals(expectedResponse.userId(), actualResponse.userId());
        assertEquals(expectedResponse.multiplications().size(), actualResponse.multiplications().size());
        assertIterableEquals(expectedResponse.multiplications(), actualResponse.multiplications());
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
        // given
        String userId = "ljs";
        String expectedMessage = "해당 ID를 가진 회원이 존재하지 않습니다.";

        given(userClient.checkUserExists(userId)).willReturn(new UserExistsResponse(false));

        // when
        UserNotFoundException thrownException = assertThrows(UserNotFoundException.class, () -> {
            multiplicationService.findRecentAttempts(userId);
        });

        // then
        assertEquals(expectedMessage, thrownException.getMessage());
    }
}