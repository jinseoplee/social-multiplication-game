package com.ljs.smg.leaderboard;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LeaderboardRow {
    private String userId;
    private long totalScore;
}
