package com.arturfrimu.exercisesback.DAO;

import com.arturfrimu.exercisesback.controller.exercise.Exercise;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class ExerciseDAO implements ExerciseDAOInterface {
    private static final Map<UUID, Exercise> exercises = new HashMap<>();

    @Override
    public Map<UUID, Exercise> getExercises() {
        return exercises;
    }

    @Override
    public void putExercise(UUID id, Exercise exercise) {
        exercises.put(id, exercise);
    }

    public List<Exercise> getAllExercises() {
        return new ArrayList<>(exercises.values());
    }


    @Override
    public Exercise getExercise(UUID id) {
        return exercises.get(id);
    }

    @Override
    public int size() {
        return exercises.size();

    }

    @Override
    public Collection<Exercise> values() {
        return exercises.values();
    }

    @Override
    public void putAll(Map<UUID, Exercise> map) {
        exercises.putAll(map);

    }

    @Override
    public void clear() {
        exercises.clear();
    }

}
