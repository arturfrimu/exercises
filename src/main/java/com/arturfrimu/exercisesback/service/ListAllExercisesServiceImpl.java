package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.DAO.ExerciseDAOInterface;
import com.arturfrimu.exercisesback.controller.exercise.Exercise;
import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListAllExercisesServiceImpl implements ListAllExercisesService {
    private final ExerciseDAOInterface exerciseDAO;

    @Override
    public List<ExerciseResponse> getAllExercises() {
        List<Exercise> allExercises = exerciseDAO.getAllExercises();
        return allExercises.stream()
                .map(exercise -> new ExerciseResponse(exercise.id(), exercise.expression(), exercise.status()))
                .toList();
    }
}
