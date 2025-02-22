package com.ljs.smg.multiplication;

import com.ljs.smg.client.UserClient;
import com.ljs.smg.client.UserExistsResponse;
import com.ljs.smg.event.EventDispatcher;
import com.ljs.smg.event.MultiplicationSolvedEvent;
import com.ljs.smg.exception.MultiplicationAttemptNotFoundException;
import com.ljs.smg.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MultiplicationService {

    private final RandomGeneratorService randomGeneratorService;
    private final MultiplicationAttemptRepository multiplicationAttemptRepository;
    private final MultiplicationMapper multiplicationMapper;
    private final UserClient userClient;
    private final EventDispatcher eventDispatcher;

    public MultiplicationResponse createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new MultiplicationResponse(factorA, factorB);
    }

    @Transactional
    public MultiplicationAttemptResponse checkAttempt(MultiplicationAttemptRequest request) {
        boolean userExists = checkUserExists(request.userId());
        if (!userExists) {
            throw new UserNotFoundException("해당 ID를 가진 회원이 존재하지 않습니다.");
        }

        boolean isCorrect = checkAnswer(request);
        MultiplicationAttempt attempt = multiplicationMapper.toMultiplicationAttempt(request, isCorrect);
        multiplicationAttemptRepository.save(attempt);

        eventDispatcher.send(new MultiplicationSolvedEvent(
                request.userId(),
                attempt.getId(),
                attempt.isCorrect()
        ));

        return new MultiplicationAttemptResponse(isCorrect);
    }

    private boolean checkUserExists(String userId) {
        UserExistsResponse userExistsResponse = userClient.checkUserExists(userId);
        return userExistsResponse.exists();
    }

    private boolean checkAnswer(MultiplicationAttemptRequest request) {
        return request.answer() == request.factorA() * request.factorB();
    }

    @Transactional(readOnly = true)
    public RecentMultiplicationAttemptResponse findRecentAttempts(String userId) {
        boolean userExists = checkUserExists(userId);
        if (!userExists) {
            throw new UserNotFoundException("해당 ID를 가진 회원이 존재하지 않습니다.");
        }

        List<MultiplicationAttempt> attempts = multiplicationAttemptRepository.findTop5ByUserIdOrderByIdDesc(userId);

        List<MultiplicationAttemptDetail> multiplications = attempts
                .stream()
                .map(attempt -> new MultiplicationAttemptDetail(
                        attempt.getId(),
                        attempt.getMultiplication().getFactorA(),
                        attempt.getMultiplication().getFactorB(),
                        attempt.getAnswer(),
                        attempt.isCorrect()
                ))
                .toList();

        return new RecentMultiplicationAttemptResponse(userId, multiplications);
    }

    @Transactional(readOnly = true)
    public MultiplicationAttemptDetail findMultiplicationAttempt(Integer attemptId) {
        return multiplicationAttemptRepository.findById(attemptId)
                .map(attempt -> new MultiplicationAttemptDetail(
                        attempt.getId(),
                        attempt.getMultiplication().getFactorA(),
                        attempt.getMultiplication().getFactorB(),
                        attempt.getAnswer(),
                        attempt.isCorrect()
                ))
                .orElseThrow(() -> new MultiplicationAttemptNotFoundException());
    }
}
