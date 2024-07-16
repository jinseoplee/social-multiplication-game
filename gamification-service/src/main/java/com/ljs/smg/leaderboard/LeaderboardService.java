package com.ljs.smg.leaderboard;

import com.ljs.smg.score.ScoreCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LeaderboardService {

    private final ScoreCardRepository scoreCardRepository;

    @Transactional(readOnly = true)
    public List<LeaderboardRow> getLeaderboard() {
        return scoreCardRepository.getLeaderboard();
    }
}
