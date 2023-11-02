package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.controller.exercise.Exercise;
import com.arturfrimu.exercisesback.controller.request.VerifyRequest;
import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;
import com.arturfrimu.exercisesback.controller.response.PercentageResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface ExerciseGenerationService {
    ExerciseResponse generateExercise(String type, String position, Integer min, Integer max);

    boolean verifyExercise(VerifyRequest verifyRequest);

    List<ExerciseResponse> getAllExercises();

    ExerciseResponse getExerciseById(UUID id);

    PercentageResponse getPercentage();

    // TODO: 30.10.2023 V-om sterge asta cand v-om adauga baza de date
    void put(Map<UUID, Exercise> map);

    // TODO: 30.10.2023 V-om sterge asta cand v-om adauga baza de date
    void clear();
}

