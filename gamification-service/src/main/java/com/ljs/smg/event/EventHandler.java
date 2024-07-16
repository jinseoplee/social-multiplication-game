package com.ljs.smg.event;

import com.ljs.smg.game.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventHandler {

    private final GameService gameService;

    @RabbitListener(queues = "${multiplication.queue}")
    void handleMultiplicationSolved(MultiplicationSolvedEvent event) {
        try {
            gameService.processCorrectAnswer(
                    event.userId(),
                    event.multiplicationAttemptId(),
                    event.isCorrect()
            );
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
