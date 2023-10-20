package com.arturfrimu.exercisesback.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RandomNumberGeneratorTest {

    @Autowired
    private RandomNumberGenerator<Integer> randomIntGenerator;

    @Autowired
    private RandomNumberGenerator<Long> randomLongGenerator;

    @Test
    void testNextInt() {
        int min = 1;
        int max = 100;
        for (int i = 0; i < 1000; i++) {
            int result = randomIntGenerator.generate(min, max);
            assertTrue(result >= min && result <= max, "Generated number should be between min and max");
        }
    }

    @Test
    void testNextLong() {
        int min = 1;
        int max = 1000;
        for (int i = 0; i < 1000; i++) {
            long result = randomLongGenerator.generate(min, max);
            assertTrue(result >= min && result <= max, "Generated number should be between min and max");
        }
    }
}