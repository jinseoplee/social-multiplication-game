package com.ljs.smg.score;

import com.ljs.smg.leaderboard.LeaderboardRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreCardRepository extends JpaRepository<ScoreCard, Integer> {

    @Query("SELECT SUM(s.score) " +
            "FROM ScoreCard s " +
            "WHERE s.userId = :userId")
    int getTotalScore(@Param("userId") String userId);

    @Query("SELECT NEW com.ljs.smg.leaderboard.LeaderboardRow(s.userId, SUM(s.score)) " +
            "FROM ScoreCard s " +
            "GROUP BY s.userId " +
            "ORDER BY SUM(s.score) DESC " +
            "LIMIT 10")
    List<LeaderboardRow> getLeaderboard();

    List<ScoreCard> findByUserIdOrderByCreatedDateDesc(String userId);
}
