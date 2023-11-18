package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.controller.exercise.Exercise;

import java.util.Map;
import java.util.UUID;

public interface ExerciseManagementService {
    void put(Map<UUID, Exercise> map);
    void clear();
}
