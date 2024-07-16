package com.ljs.smg.leaderboard;

import com.ljs.smg.score.ScoreCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.BDDMockito.given;

class LeaderboardServiceTest {

    private LeaderboardService leaderboardService;

    @Mock
    private ScoreCardRepository scoreCardRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        leaderboardService = new LeaderboardService(scoreCardRepository);
    }

    @Test
    public void getLeaderboardTest() {
        // given
        List<LeaderboardRow> expectedLeaderboard = List.of(
                new LeaderboardRow("user 1", 100),
                new LeaderboardRow("user 2", 120),
                new LeaderboardRow("user 3", 20)
        );

        given(scoreCardRepository.getLeaderboard())
                .willReturn(expectedLeaderboard);

        // when
        List<LeaderboardRow> actualLeaderboard = leaderboardService.getLeaderboard();

        // then
        assertEquals(expectedLeaderboard.size(), actualLeaderboard.size());
        assertIterableEquals(expectedLeaderboard, actualLeaderboard);
    }
}