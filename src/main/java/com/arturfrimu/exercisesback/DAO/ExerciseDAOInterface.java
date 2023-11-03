package com.arturfrimu.exercisesback.DAO;

import com.arturfrimu.exercisesback.controller.exercise.Exercise;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ExerciseDAOInterface {
    Map<UUID, Exercise> getExercises();

    void putExercise(UUID id, Exercise exercise);

    public List<Exercise> getAllExercises();

    Exercise getExercise(UUID id);

    int size();

    void putAll(Map<UUID, Exercise> map);

    void clear();
}

