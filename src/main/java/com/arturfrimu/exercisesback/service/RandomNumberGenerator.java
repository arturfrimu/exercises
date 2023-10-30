package com.arturfrimu.exercisesback.service;

import org.springframework.stereotype.Service;

import java.util.Random;

public interface RandomNumberGenerator<T> {

    T generate(int min, int max);

    T generate();
}
