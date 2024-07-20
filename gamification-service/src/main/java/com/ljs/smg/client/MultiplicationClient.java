package com.ljs.smg.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "multiplication-service"
)
public interface MultiplicationClient {

    @GetMapping("/api/v1/multiplication/attempts/{attemptId}")
    MultiplicationAttemptDetail getMultiplicationAttempt(@PathVariable("attemptId") Integer attemptId);
}
