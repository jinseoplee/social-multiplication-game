package com.ljs.smg.multiplication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/multiplication")
public class MultiplicationController {

    private final MultiplicationService multiplicationService;

    @GetMapping("/random")
    public ResponseEntity<MultiplicationResponse> getRandomMultiplication() {
        return ResponseEntity.ok(multiplicationService.createRandomMultiplication());
    }
}
