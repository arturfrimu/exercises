package com.arturfrimu.exercisesback.dao;

import com.arturfrimu.exercisesback.entity.ExerciseEntity;
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

    /**
     * @param id
     * @return Din DAO returnam Optional<Exercise> si lasam service-ul care raspunde de business logica sa
     * decida ce sa faca daca nu a fost gasit nici un exercitiu cu acest Id .
     * Incata despre Optional
     * Optional.isPresent()
     * Optional.isEmpty()
     * Optional.orElse()
     * Optional.orElseThrow()
     * Optional.map()
     */
    public Optional<Exercise> getExercise(UUID id) {
        return exerciseRepository.findById(id)
                .map(exercise -> new Exercise(
                        exercise.getId(),
                        exercise.getExpression(),
                        exercise.getResult(),
                        exercise.getStatus()));
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


