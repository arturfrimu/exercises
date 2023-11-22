package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;

import java.util.UUID;

public interface FindExerciseService {
    ExerciseResponse findById(UUID id);
}
