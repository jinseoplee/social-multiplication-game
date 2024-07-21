package com.ljs.smg.game;

import com.ljs.smg.badge.Badge;
import com.ljs.smg.badge.BadgeCard;
import com.ljs.smg.badge.BadgeCardRepository;
import com.ljs.smg.exception.UserNotFoundException;
import com.ljs.smg.score.ScoreCardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private ScoreCardRepository scoreCardRepository;

    @Mock
    private BadgeCardRepository badgeCardRepository;

    @Test
    void getUserStatisticsTest() {
        // given
        String userId = "ljs";
        int totalScore = 100;
        List<BadgeCard> badges = List.of(
                new BadgeCard(userId, Badge.FIRST_WON),
                new BadgeCard(userId, Badge.BRONZE)
        );
        GameStatistics expectedGameStatistics = new GameStatistics(userId, totalScore, badges.stream()
                .map(BadgeCard::getBadge)
                .toList());

        given(scoreCardRepository.getTotalScore(userId))
                .willReturn(Optional.of(totalScore));
        given(badgeCardRepository.findByUserIdOrderByCreatedDateDesc(userId))
                .willReturn(badges);


        // when
        GameStatistics actualGameStatistics = gameService.getUserStatistics(userId);

        // then
        assertEquals(expectedGameStatistics.getUserId(), actualGameStatistics.getUserId());
        assertEquals(expectedGameStatistics.getTotalScore(), actualGameStatistics.getTotalScore());
        assertIterableEquals(expectedGameStatistics.getBadges(), actualGameStatistics.getBadges());
    }

    @Test
    void userNotFoundTest() {
        // given
        String userId = "nonExistentUser";
        given(scoreCardRepository.getTotalScore(userId))
                .willReturn(Optional.empty());

        // when & then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            gameService.getUserStatistics(userId);
        });

        assertEquals("존재하지 않는 회원입니다.", exception.getMessage());
    }
}