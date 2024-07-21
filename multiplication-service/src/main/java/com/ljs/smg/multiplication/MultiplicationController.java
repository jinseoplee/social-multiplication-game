package com.ljs.smg.multiplication;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/multiplication")
public class MultiplicationController {

    private final MultiplicationService multiplicationService;

    @GetMapping("/random")
    public ResponseEntity<MultiplicationResponse> getRandomMultiplication() {
        return ResponseEntity.ok(multiplicationService.createRandomMultiplication());
    }

    @PostMapping("/attempts")
    public ResponseEntity<MultiplicationAttemptResponse> checkAttempt(@RequestBody @Valid MultiplicationAttemptRequest request) {
        return ResponseEntity.ok(multiplicationService.checkAttempt(request));
    }

    @GetMapping("/attempts/results/{userId}")
    public ResponseEntity<RecentMultiplicationAttemptResponse> getRecentAttempts(@PathVariable(name = "userId") String userId) {
        return ResponseEntity.ok(multiplicationService.findRecentAttempts(userId));
    }

    @GetMapping("/attempts/{attemptId}")
    public ResponseEntity<MultiplicationAttemptDetail> getMultiplicationAttempt(@PathVariable("attemptId") Integer attemptId) {
        return ResponseEntity.ok(multiplicationService.findMultiplicationAttempt(attemptId));
    }
}
