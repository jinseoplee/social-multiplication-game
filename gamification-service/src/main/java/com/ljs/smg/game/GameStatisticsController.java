package com.ljs.smg.game;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/gamification/statistics")
public class GameStatisticsController {

    private final GameService gameService;

    @GetMapping
    public ResponseEntity<GameStatistics> getStatistics(@RequestParam("userId") String userId) {
        return ResponseEntity.ok(gameService.getUserStatistics(userId));
    }
}
