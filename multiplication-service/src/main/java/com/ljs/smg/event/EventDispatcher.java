package com.ljs.smg.event;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventDispatcher {

    @Value("${multiplication.exchange}")
    private String multiplicationExchange;

    @Value("${multiplication.routing-key}")
    private String multiplicationRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public void send(MultiplicationSolvedEvent multiplicationSolvedEvent) {
        rabbitTemplate.convertAndSend(
                multiplicationExchange,
                multiplicationRoutingKey,
                multiplicationSolvedEvent
        );
    }
}
