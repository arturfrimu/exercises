package com.arturfrimu.exercisesback.service;

public interface RandomNumberGenerator<T> {

    T generate(int min, int max);

    T generate();
}
