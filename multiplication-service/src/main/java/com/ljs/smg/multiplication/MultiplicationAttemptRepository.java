package com.ljs.smg.multiplication;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MultiplicationAttemptRepository extends JpaRepository<MultiplicationAttempt, Integer> {

    List<MultiplicationAttempt> findTop5ByUserIdOrderByIdDesc(String userId);
}
