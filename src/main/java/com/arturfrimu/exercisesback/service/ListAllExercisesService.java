package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;

import java.util.List;

public interface ListAllExercisesService {
    List<ExerciseResponse> getAllExercises();
}
