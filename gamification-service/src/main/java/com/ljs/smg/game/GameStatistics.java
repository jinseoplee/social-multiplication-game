package com.ljs.smg.game;

import com.ljs.smg.badge.Badge;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GameStatistics {
    private String userId;
    private int totalScore;
    private List<Badge> badges;
}
