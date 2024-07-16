package com.ljs.smg.badge;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeCardRepository extends JpaRepository<BadgeCard, Integer> {

    List<BadgeCard> findByUserIdOrderByCreatedDateDesc(String userId);
}
