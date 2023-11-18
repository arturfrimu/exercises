package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.controller.request.VerifyRequest;
import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;

public interface GenerateExerciseService {
    ExerciseResponse generateExercise();
    boolean verifyExercise(VerifyRequest verifyRequest);
}
