package com.arturfrimu.exercisesback.DAO;

import com.arturfrimu.exercisesback.Entity.ExerciseEntity;
import com.arturfrimu.exercisesback.controller.exercise.Exercise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
@RequiredArgsConstructor
public class ExerciseDAOImpl {
    private final ExerciseRepository exerciseRepository;

    public void putExercise(UUID id,Exercise exercise) {
        ExerciseEntity exerciseEntity = new ExerciseEntity(
                exercise.id(),
                exercise.expression(),
                exercise.result(),
                exercise.status());
        exerciseRepository.save(exerciseEntity);
    }

    public List<Exercise> getAllExercises() {
        List<ExerciseEntity> exerciseEntities = exerciseRepository.findAll();

        List<Exercise> exercises = exerciseEntities.stream()
                .map(exerciseEntity ->
                        new Exercise(
                                exerciseEntity.getId(),
                                exerciseEntity.getExpression(),
                                exerciseEntity.getResult(),
                                exerciseEntity.getStatus())).toList();
        return exercises;
    }

    public Exercise getExercise(UUID id) {
        ExerciseEntity exerciseEntity = null;
        Optional<ExerciseEntity> optional = exerciseRepository.findById(id);

        if (optional.isPresent()) {
            exerciseEntity = optional.get();
        }
        Exercise exercise = new Exercise(
                exerciseEntity.getId(),
                exerciseEntity.getExpression(),
                exerciseEntity.getResult(),
                exerciseEntity.getStatus());
        return exercise;

    }

    public long size() {
        return exerciseRepository.count();
    }

    public void putAll(List<Exercise> exercises) {
        List<ExerciseEntity> exerciseEntities = exercises
                .stream()
                .map(exercise -> new ExerciseEntity(
                        exercise.id(),
                        exercise.expression(),
                        exercise.result(),
                        exercise.status())
                )
                .toList();
        exerciseRepository.saveAll(exerciseEntities);
    }
}


