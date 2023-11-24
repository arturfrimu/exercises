package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.controller.request.VerifyRequest;

public interface VerifyExerciseService {
    boolean verify(VerifyRequest verifyRequest);
}
