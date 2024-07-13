package com.ljs.smg.multiplication;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


class RandomGeneratorServiceTest {

    private RandomGeneratorService randomGeneratorService = new RandomGeneratorService();

    @Test
    public void generateRandomFactorTest() {
        // 1000개의 난수 생성
        List<Integer> factors = IntStream.range(0, 1000)
                .mapToObj(i -> randomGeneratorService.generateRandomFactor())
                .toList();

        // 모든 요소가 1 이상 9 이하인지 검증
        assertThat(factors)
                .allMatch(factor -> 1 <= factor && factor <= 9);
    }
}