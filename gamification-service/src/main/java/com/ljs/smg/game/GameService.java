package com.ljs.smg.game;

import com.ljs.smg.badge.Badge;
import com.ljs.smg.badge.BadgeCard;
import com.ljs.smg.badge.BadgeCardRepository;
import com.ljs.smg.score.ScoreCard;
import com.ljs.smg.score.ScoreCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameService {

    private final ScoreCardRepository scoreCardRepository;
    private final BadgeCardRepository badgeCardRepository;

    @Transactional
    public void processCorrectAnswer(String userId, Integer attemptId, boolean isCorrect) {
        if (isCorrect) {
            ScoreCard scoreCard = new ScoreCard(userId, attemptId);
            scoreCardRepository.save(scoreCard);
            log.info("사용자 ID: {}, 점수: {}점, 답안 ID: {}", userId, scoreCard.getScore(), attemptId);

            int totalScore = scoreCardRepository.getTotalScore(userId);
            log.info("사용자 ID: {}, 총 점수: {}", userId, totalScore);

            List<BadgeCard> badgeCards = processBadges(userId, totalScore, attemptId);
        }
    }

    private List<BadgeCard> processBadges(String userId, int totalScore, Integer attemptId) {
        List<BadgeCard> badgeCards = new ArrayList<>();

        List<ScoreCard> scoreCardList = scoreCardRepository
                .findByUserIdOrderByCreatedDateDesc(userId);
        List<BadgeCard> badgeCardList = badgeCardRepository
                .findByUserIdOrderByCreatedDateDesc(userId);

        // 점수 기반 배지
        checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.BRONZE, totalScore, 100, userId)
                .ifPresent(badgeCard -> badgeCards.add(badgeCard));
        checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.SILVER, totalScore, 500, userId)
                .ifPresent(badgeCard -> badgeCards.add(badgeCard));
        checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.GOLD, totalScore, 1000, userId)
                .ifPresent(badgeCard -> badgeCards.add(badgeCard));

        // 첫 번째 정답 배지
        if (scoreCardList.size() == 1 && !containsBadge(badgeCardList, Badge.FIRST_WON)) {
            BadgeCard firstWonBadge = giveBadgeToUser(userId, Badge.FIRST_WON);
            badgeCards.add(firstWonBadge);
        }

        return badgeCards;
    }

    /**
     * 배지를 얻기 위한 조건을 만족하는지 체크하는 메서드
     * 조건이 만족되면 사용자에게 배지를 부여
     */
    private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(List<BadgeCard> badgeCards, Badge badge,
                                                              int score, int scoreThreshold, String userId) {
        if (score >= scoreThreshold && !containsBadge(badgeCards, badge)) {
            return Optional.of(giveBadgeToUser(userId, badge));
        }
        return Optional.empty();
    }

    /**
     * 배지 목록에 해당 배지가 포함돼 있는지 확인하는 메서드
     */
    private boolean containsBadge(List<BadgeCard> badgeCards, Badge badge) {
        return badgeCards
                .stream()
                .anyMatch(b -> b.getBadge().equals(badge));
    }

    /**
     * 주어진 사용자에게 새로운 배지를 부여하는 메서드
     */
    private BadgeCard giveBadgeToUser(String userId, Badge badge) {
        BadgeCard badgeCard = new BadgeCard(userId, badge);
        badgeCardRepository.save(badgeCard);
        log.info("사용자 ID: {}, 새로운 배지 획득: {}", userId, badge);
        return badgeCard;
    }
}
