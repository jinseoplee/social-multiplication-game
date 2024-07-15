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

    @PostMapping("/attempt")
    public ResponseEntity<MultiplicationAttemptResponse> checkAttempt(@RequestBody @Valid MultiplicationAttemptRequest request) {
        return ResponseEntity.ok(multiplicationService.checkAttempt(request));
    }

    @GetMapping("/attempt/results/{userId}")
    public ResponseEntity<RecentMultiplicationAttemptResponse> getRecentAttempts(@PathVariable(name = "userId") String userId) {
        return ResponseEntity.ok(multiplicationService.findRecentAttempts(userId));
    }
}
