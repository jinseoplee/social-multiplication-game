package com.ljs.smg.multiplication;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomGeneratorService {

    private final Random random = new Random();
    private final int MINIMUM_FACTOR = 1;
    private final int MAXIMUM_FACTOR = 9;

    public int generateRandomFactor() {
        return random.nextInt(MAXIMUM_FACTOR - MINIMUM_FACTOR + 1) + MINIMUM_FACTOR;
    }
}
