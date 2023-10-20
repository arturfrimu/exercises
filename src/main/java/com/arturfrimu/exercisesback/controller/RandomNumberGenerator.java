package com.arturfrimu.exercisesback.controller;

import org.springframework.stereotype.Service;

import java.util.Random;

public interface RandomNumberGenerator<T> {

    T generate(int min, int max);

    T generate();

    @Service
    class RandomIntGenerator implements RandomNumberGenerator<Integer> {
        public static final Random random = new Random();

        @Override
        public Integer generate(int min, int max) {
            return random.nextInt((max - min) + 1) + min;
        }

        @Override
        public Integer generate() {
            return random.nextInt();
        }
    }

    @Service
    class RandomLongGenerator implements RandomNumberGenerator<Long> {
        public static final Random random = new Random();

        @Override
        public Long generate(int min, int max) {
            return min + (long) (Math.random() * ((max - min) + 1));
        }

        @Override
        public Long generate() {
            return (long) (Math.random() * (Long.MAX_VALUE));
        }
    }
}